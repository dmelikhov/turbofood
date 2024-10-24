package turbofood.restaurant.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import turbofood.restaurant.dto.RestaurantDto;
import turbofood.restaurant.dto.RestaurantWithMenuDto;
import turbofood.restaurant.entity.MenuItem;
import turbofood.restaurant.entity.Restaurant;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantDto restaurantToRestaurantDto(Restaurant restaurant);

    RestaurantWithMenuDto restaurantToRestaurantWithMenuDto(Restaurant restaurant, List<MenuItem> menu);

    Restaurant restaurantDtoToRestaurant(RestaurantDto restaurantDto);

}
