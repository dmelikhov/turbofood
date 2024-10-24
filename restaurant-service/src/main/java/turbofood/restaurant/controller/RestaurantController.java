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
import turbofood.restaurant.dto.RestaurantDto;
import turbofood.restaurant.dto.RestaurantWithMenuDto;
import turbofood.restaurant.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
@SecurityRequirement(name = "openid-connect")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public RestaurantDto create(@RequestBody RestaurantDto restaurantDto) {
        return restaurantService.create(restaurantDto);
    }

    @GetMapping
    public List<RestaurantDto> findAll() {
        return restaurantService.findAll();
    }

    @GetMapping("/{id}")
    public RestaurantWithMenuDto findById(@PathVariable UUID id) {
        return restaurantService.findById(id);
    }

    @PutMapping("/{id}")
    public RestaurantDto update(@PathVariable UUID id, @RequestBody RestaurantDto restaurantDto) {
        return restaurantService.update(id, restaurantDto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        restaurantService.deleteById(id);
    }

}
