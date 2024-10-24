package turbofood.delivery.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import turbofood.delivery.entity.DeliveryAssignment;

public interface DeliveryAssignmentRepository extends JpaRepository<DeliveryAssignment, UUID> {
}
