package turbofood.delivery.service;

import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import turbofood.delivery.dto.AssignCourierRequest;
import turbofood.delivery.dto.DeliveryAssignmentDto;
import turbofood.delivery.entity.DeliveryAssignment;
import turbofood.delivery.entity.DeliveryStatus;
import turbofood.delivery.event.CourierArrivedEvent;
import turbofood.delivery.event.CourierAssignedEvent;
import turbofood.delivery.event.OrderPreparedEvent;
import turbofood.delivery.mapper.DeliveryAssignmentMapper;
import turbofood.delivery.repository.DeliveryAssignmentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryAssignmentRepository deliveryAssignmentRepository;
    private final DeliveryAssignmentMapper deliveryAssignmentMapper;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "order.prepared")
    public void onOrderPrepared(OrderPreparedEvent event) {
        DeliveryAssignment deliveryAssignment = new DeliveryAssignment();
        deliveryAssignment.setOrderId(event.getOrderId());
        deliveryAssignment.setStatus(DeliveryStatus.UNASSIGNED);
        deliveryAssignmentRepository.save(deliveryAssignment);
    }

    public List<DeliveryAssignmentDto> findAllUnassigned() {
        return deliveryAssignmentRepository.findAllByStatus(DeliveryStatus.UNASSIGNED).stream()
                .map(deliveryAssignmentMapper::deliveryAssignmentToDeliveryAssignmentDto)
                .toList();
    }

    public DeliveryAssignmentDto assign(UUID id, AssignCourierRequest request) {
        DeliveryAssignment deliveryAssignment = deliveryAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        deliveryAssignment.setCourierId(request.getCourierId());
        deliveryAssignment.setStatus(DeliveryStatus.ASSIGNED);
        deliveryAssignmentRepository.save(deliveryAssignment);
        rabbitTemplate.convertAndSend("turbofood", "courier.assigned", new CourierAssignedEvent(deliveryAssignment.getOrderId()));
        return deliveryAssignmentMapper.deliveryAssignmentToDeliveryAssignmentDto(deliveryAssignment);
    }

    public DeliveryAssignmentDto arrive(UUID id) {
        DeliveryAssignment deliveryAssignment = deliveryAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        deliveryAssignment.setStatus(DeliveryStatus.ARRIVED);
        deliveryAssignmentRepository.save(deliveryAssignment);
        rabbitTemplate.convertAndSend("turbofood", "courier.arrived", new CourierArrivedEvent(deliveryAssignment.getOrderId()));
        return deliveryAssignmentMapper.deliveryAssignmentToDeliveryAssignmentDto(deliveryAssignment);
    }

}
