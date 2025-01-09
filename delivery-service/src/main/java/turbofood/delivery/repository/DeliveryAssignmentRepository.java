package turbofood.delivery.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import turbofood.delivery.entity.DeliveryAssignment;
import turbofood.delivery.entity.DeliveryStatus;

public interface DeliveryAssignmentRepository extends JpaRepository<DeliveryAssignment, UUID> {

    List<DeliveryAssignment> findAllByStatus(DeliveryStatus status);

}
