package turbofood.delivery.service;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import turbofood.delivery.dto.AssignCourierRequest;
import turbofood.delivery.entity.DeliveryAssignment;
import turbofood.delivery.entity.DeliveryStatus;
import turbofood.delivery.event.CourierArrivedEvent;
import turbofood.delivery.event.CourierAssignedEvent;
import turbofood.delivery.repository.DeliveryAssignmentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryAssignmentRepository deliveryAssignmentRepository;
    private final RabbitTemplate rabbitTemplate;

    public void assign(AssignCourierRequest request) {
        DeliveryAssignment deliveryAssignment = new DeliveryAssignment();
        deliveryAssignment.setOrderId(request.getOrderId());
        deliveryAssignment.setStatus(DeliveryStatus.ASSIGNED);
        deliveryAssignmentRepository.save(deliveryAssignment);
        rabbitTemplate.convertAndSend("order-events", new CourierAssignedEvent(deliveryAssignment.getOrderId()));
    }

    public void arrive(UUID id) {
        DeliveryAssignment deliveryAssignment = deliveryAssignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        deliveryAssignment.setStatus(DeliveryStatus.ARRIVED);
        deliveryAssignmentRepository.save(deliveryAssignment);
        rabbitTemplate.convertAndSend("order-events", new CourierArrivedEvent(deliveryAssignment.getOrderId()));
    }

}
