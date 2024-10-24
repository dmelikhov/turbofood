package turbofood.payment.event;

import java.util.UUID;

public class OrderRejectedEvent {
    private final UUID orderId;

    public OrderRejectedEvent(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}
