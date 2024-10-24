package turbofood.payment.event;

import java.util.UUID;

public class OrderCreatedEvent {
    private final UUID orderId;

    public OrderCreatedEvent(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}
