package turbofood.restaurant.service;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import turbofood.restaurant.dto.MenuItemDto;
import turbofood.restaurant.entity.MenuItem;
import turbofood.restaurant.entity.Restaurant;
import turbofood.restaurant.mapper.MenuItemMapper;
import turbofood.restaurant.repository.MenuItemRepository;
import turbofood.restaurant.repository.RestaurantRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuItemService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    public MenuItemDto create(UUID restaurantId, MenuItemDto menuItemDto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        MenuItem menuItem = menuItemMapper.menuItemDtoToMenuItem(menuItemDto);
        menuItem.setId(null);
        menuItem.setRestaurant(restaurant);
        menuItem = menuItemRepository.save(menuItem);
        return menuItemMapper.menuItemToMenuItemDto(menuItem);
    }

    public List<MenuItemDto> findAll(UUID restaurantId) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return menuItemRepository.findAllByRestaurantId(restaurantId).stream()
                .map(menuItemMapper::menuItemToMenuItemDto)
                .toList();
    }

    public MenuItemDto findById(UUID restaurantId, UUID id) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return menuItemRepository.findById(id)
                .map(menuItemMapper::menuItemToMenuItemDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public MenuItemDto update(UUID restaurantId, UUID id, MenuItemDto menuItemDto) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (!menuItemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        MenuItem menuItem = menuItemMapper.menuItemDtoToMenuItem(menuItemDto);
        menuItem.setId(id);
        menuItem = menuItemRepository.save(menuItem);
        return menuItemMapper.menuItemToMenuItemDto(menuItem);
    }

    public void deleteById(UUID restaurantId, UUID id) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        menuItemRepository.deleteById(id);
    }

}
