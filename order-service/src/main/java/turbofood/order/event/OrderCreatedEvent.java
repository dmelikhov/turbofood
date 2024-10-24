package turbofood.order.event;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderCreatedEvent {
    private final UUID orderId;
}
