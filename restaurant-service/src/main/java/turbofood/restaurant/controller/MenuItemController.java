package turbofood.restaurant.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import turbofood.restaurant.dto.MenuItemDto;
import turbofood.restaurant.service.MenuItemService;

@RestController
@RequestMapping("/restaurants/{restaurantId}/menu-items")
@SecurityRequirement(name = "openid-connect")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping
    public MenuItemDto create(@PathVariable UUID restaurantId, @RequestBody MenuItemDto menuItemDto) {
        return menuItemService.create(restaurantId, menuItemDto);
    }

    @GetMapping
    public List<MenuItemDto> findAll(@PathVariable UUID restaurantId) {
        return menuItemService.findAll(restaurantId);
    }

    @GetMapping("/{id}")
    public MenuItemDto findById(@PathVariable UUID restaurantId, @PathVariable UUID id) {
        return menuItemService.findById(restaurantId, id);
    }

    @PutMapping("/{id}")
    public MenuItemDto update(@PathVariable UUID restaurantId, @PathVariable UUID id, @RequestBody MenuItemDto menuItemDto) {
        return menuItemService.update(restaurantId, id, menuItemDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID restaurantId, @PathVariable UUID id) {
        menuItemService.deleteById(restaurantId, id);
    }

}
