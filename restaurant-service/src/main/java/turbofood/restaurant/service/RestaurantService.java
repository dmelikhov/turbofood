package turbofood.restaurant.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import turbofood.restaurant.dto.RestaurantDto;
import turbofood.restaurant.dto.RestaurantWithMenuDto;
import turbofood.restaurant.entity.MenuItem;
import turbofood.restaurant.entity.Restaurant;
import turbofood.restaurant.mapper.RestaurantMapper;
import turbofood.restaurant.repository.MenuItemRepository;
import turbofood.restaurant.repository.RestaurantRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final MenuItemRepository menuItemRepository;

    public RestaurantDto create(RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantMapper.restaurantDtoToRestaurant(restaurantDto);
        restaurant.setId(null);
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.restaurantToRestaurantDto(restaurant);
    }

    public List<RestaurantDto> findAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::restaurantToRestaurantDto)
                .toList();
    }

    public RestaurantWithMenuDto findById(UUID id) {
        return restaurantRepository.findById(id)
                .map(restaurant -> {
                    List<MenuItem> menu = menuItemRepository.findAllByRestaurantId(id);
                    return restaurantMapper.restaurantToRestaurantWithMenuDto(restaurant, menu);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public RestaurantDto update(UUID id, RestaurantDto restaurantDto) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Restaurant restaurant = restaurantMapper.restaurantDtoToRestaurant(restaurantDto);
        restaurant.setId(id);
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.restaurantToRestaurantDto(restaurant);
    }

    public void deleteById(UUID id) {
        restaurantRepository.deleteById(id);
    }

}
