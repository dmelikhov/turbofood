package turbofood.restaurant.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import turbofood.restaurant.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

    List<MenuItem> findAllByRestaurantId(UUID restaurantId);

}
