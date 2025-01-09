package turbofood.order.service;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.RequiredArgsConstructor;
import turbofood.order.dto.CreateOrderRequest;
import turbofood.order.dto.MenuItemDto;
import turbofood.order.dto.RestaurantWithMenuDto;
import turbofood.order.entity.OrderItem;

@Service
@RequiredArgsConstructor
public class RestaurantIntegrationServiceImpl implements RestaurantIntegrationService {

    private final DiscoveryClient discoveryClient;

    @Override
    public List<OrderItem> getOrderItems(CreateOrderRequest request) {
        return request.getMenuItemIds().stream()
                .map(menuItemId -> getOrderItem(request.getRestaurantId(), menuItemId))
                .toList();
    }

    private OrderItem getOrderItem(UUID restaurantId, UUID menuItemId) {
        ServiceInstance serviceInstance = discoveryClient.getInstances("restaurant-service").get(0);
        String authorization = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getHeader("Authorization");
        RestClient restClient = RestClient.create();
        MenuItemDto menuItem = restClient.get()
                .uri(serviceInstance.getUri() + "/restaurants/{restaurantId}/menu-items/{menuItemId}", restaurantId, menuItemId)
                .header("Authorization", authorization)
                .retrieve()
                .body(MenuItemDto.class);
        OrderItem orderItem = new OrderItem();
        orderItem.setName(menuItem.getName());
        orderItem.setPrice(menuItem.getPrice());
        return orderItem;
    }

    @Override
    public String getRestaurantAddress(CreateOrderRequest request) {
        ServiceInstance serviceInstance = discoveryClient.getInstances("restaurant-service").get(0);
        String authorization = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
                .getHeader("Authorization");
        RestClient restClient = RestClient.create();
        RestaurantWithMenuDto restaurant = restClient.get()
                .uri(serviceInstance.getUri() + "/restaurants/{restaurantId}", request.getRestaurantId())
                .header("Authorization", authorization)
                .retrieve()
                .body(RestaurantWithMenuDto.class);
        return restaurant.getAddress();
    }

}
