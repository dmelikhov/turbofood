package turbofood.order.service;

import java.util.List;

import turbofood.order.dto.CreateOrderRequest;
import turbofood.order.entity.OrderItem;

public interface RestaurantIntegrationService {

    List<OrderItem> getOrderItems(CreateOrderRequest request);

    String getRestaurantAddress(CreateOrderRequest request);

}
