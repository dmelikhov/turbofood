package turbofood.order.service;

import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import turbofood.order.dto.CreateOrderRequest;
import turbofood.order.dto.OrderDto;
import turbofood.order.entity.Order;
import turbofood.order.entity.OrderItem;
import turbofood.order.entity.OrderStatus;
import turbofood.order.event.CourierArrivedEvent;
import turbofood.order.event.CourierAssignedEvent;
import turbofood.order.event.OrderConfirmedEvent;
import turbofood.order.event.OrderCreatedEvent;
import turbofood.order.event.OrderPreparedEvent;
import turbofood.order.event.OrderRejectedEvent;
import turbofood.order.event.PaymentFailedEvent;
import turbofood.order.event.PaymentReceivedEvent;
import turbofood.order.mapper.OrderMapper;
import turbofood.order.repository.OrderRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestaurantIntegrationService restaurantIntegrationService;
    private final RabbitTemplate rabbitTemplate;

    public OrderDto create(CreateOrderRequest request) {
        Order order = orderMapper.createOrderRequestToOrder(request);
        order.setItems(restaurantIntegrationService.getOrderItems(request));
        order.setAddressFrom(restaurantIntegrationService.getRestaurantAddress(request));
        order.setTotalPrice(order.getItems().stream().map(OrderItem::getPrice).reduce(0.0, Double::sum));
        order.setStatus(OrderStatus.AWAITING_PAYMENT);
        order = orderRepository.save(order);
        rabbitTemplate.convertAndSend("turbofood", "order.created", new OrderCreatedEvent(order.getId()));
        return orderMapper.orderToOrderDto(order);
    }

    public OrderDto findById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return orderMapper.orderToOrderDto(order);
    }

    @RabbitListener(queues = "payment.received")
    public void onPaymentReceived(PaymentReceivedEvent event) {
        updateStatus(event.getOrderId(), OrderStatus.AWAITING_PAYMENT, OrderStatus.AWAITING_CONFIRMATION);
    }

    @RabbitListener(queues = "payment.failed")
    public void onPaymentFailed(PaymentFailedEvent event) {
        updateStatus(event.getOrderId(), OrderStatus.AWAITING_PAYMENT, OrderStatus.CANCELED);
    }

    public OrderDto confirm(UUID id) {
        return updateStatus(id, OrderStatus.AWAITING_CONFIRMATION, OrderStatus.AWAITING_PREPARATION,
                "order.confirmed",
                new OrderConfirmedEvent(id));
    }

    public OrderDto reject(UUID id) {
        return updateStatus(id, OrderStatus.AWAITING_CONFIRMATION, OrderStatus.CANCELED,
                "order.rejected",
                new OrderRejectedEvent(id));
    }

    public OrderDto prepare(UUID id) {
        return updateStatus(id, OrderStatus.AWAITING_PREPARATION, OrderStatus.AWAITING_COURIER,
                "order.prepared",
                new OrderPreparedEvent(id));
    }

    @RabbitListener(queues = "courier.assigned")
    public void onCourierAssigned(CourierAssignedEvent event) {
        updateStatus(event.getOrderId(), OrderStatus.AWAITING_COURIER, OrderStatus.AWAITING_DELIVERY);
    }

    @RabbitListener(queues = "courier.arrived")
    public void onCourierArrived(CourierArrivedEvent event) {
        updateStatus(event.getOrderId(), OrderStatus.AWAITING_DELIVERY, OrderStatus.COMPLETED);
    }

    private OrderDto updateStatus(UUID id, OrderStatus expectedStatus, OrderStatus newStatus) {
        return updateStatus(id, expectedStatus, newStatus, null, null);
    }

    private OrderDto updateStatus(UUID id, OrderStatus expectedStatus, OrderStatus newStatus, String routingKey, Object event) {
        Order order = orderRepository.findById(id).orElseThrow();
        if (order.getStatus() != expectedStatus) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        order.setStatus(newStatus);
        order = orderRepository.save(order);
        if (event != null) {
            rabbitTemplate.convertAndSend("turbofood", routingKey, event);
        }
        return orderMapper.orderToOrderDto(order);
    }

}
