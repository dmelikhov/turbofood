package turbofood.order.event;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderRejectedEvent {
    private final UUID orderId;
}
