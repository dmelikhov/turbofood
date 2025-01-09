package turbofood.order.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private UUID restaurantId;
    private UUID customerId;
    private List<UUID> menuItemIds;
    private String addressTo;
}
