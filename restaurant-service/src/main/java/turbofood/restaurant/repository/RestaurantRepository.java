package turbofood.restaurant.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import turbofood.restaurant.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
}
