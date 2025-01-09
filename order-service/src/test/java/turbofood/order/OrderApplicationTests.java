package turbofood.order;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import turbofood.order.dto.CreateOrderRequest;
import turbofood.order.dto.OrderDto;
import turbofood.order.entity.Order;
import turbofood.order.entity.OrderStatus;
import turbofood.order.event.*;
import turbofood.order.mapper.OrderMapper;
import turbofood.order.repository.OrderRepository;
import turbofood.order.service.OrderService;
import turbofood.order.service.RestaurantIntegrationService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class OrderApplicationTests {

	@Test
	void testOrderFlow() {
		OrderRepository orderRepository = mock(OrderRepository.class);
		OrderMapper orderMapper = mock(OrderMapper.class);
		RestaurantIntegrationService restaurantIntegrationService = mock(RestaurantIntegrationService.class);
		RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
		OrderService orderService = new OrderService(orderRepository, orderMapper, restaurantIntegrationService, rabbitTemplate);

		// given
		Order order = new Order();
		order.setId(UUID.randomUUID());
		when(orderMapper.createOrderRequestToOrder(any())).thenReturn(order);
		when(orderRepository.save(any())).thenReturn(order);
		when(orderRepository.findById(any())).thenReturn(Optional.of(order));

		// when
		orderService.create(new CreateOrderRequest());
		// then
		assertEquals(OrderStatus.AWAITING_PAYMENT, order.getStatus());
		verify(rabbitTemplate).convertAndSend("turbofood", "order.created", new OrderCreatedEvent(order.getId()));

		// when
		orderService.onPaymentReceived(new PaymentReceivedEvent(order.getId()));
		// then
		assertEquals(OrderStatus.AWAITING_CONFIRMATION, order.getStatus());

		// when
		orderService.confirm(order.getId());
		// then
		assertEquals(OrderStatus.AWAITING_PREPARATION, order.getStatus());
		verify(rabbitTemplate).convertAndSend("turbofood", "order.confirmed", new OrderConfirmedEvent(order.getId()));

		// when
		orderService.prepare(order.getId());
		// then
		assertEquals(OrderStatus.AWAITING_COURIER, order.getStatus());
		verify(rabbitTemplate).convertAndSend("turbofood", "order.prepared", new OrderPreparedEvent(order.getId()));

		// when
		orderService.onCourierAssigned(new CourierAssignedEvent(order.getId()));
		// then
		assertEquals(OrderStatus.AWAITING_DELIVERY, order.getStatus());

		// when
		orderService.onCourierArrived(new CourierArrivedEvent(order.getId()));
		// then
		assertEquals(OrderStatus.COMPLETED, order.getStatus());
	}

}
