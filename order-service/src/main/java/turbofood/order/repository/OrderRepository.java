package turbofood.order.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import turbofood.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
