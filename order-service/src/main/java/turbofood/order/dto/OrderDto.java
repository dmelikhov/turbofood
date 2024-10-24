package turbofood.order.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import turbofood.order.entity.OrderStatus;

@Data
public class OrderDto {
    private UUID id;
    private UUID restaurantId;
    private UUID customerId;
    private String addressFrom;
    private String addressTo;
    private List<OrderItemDto> items;
    private Double totalPrice;
    private OrderStatus status;
}
