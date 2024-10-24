package turbofood.payment.event;

import java.util.UUID;

public class PaymentFailedEvent {
    private final UUID orderId;

    public PaymentFailedEvent(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}
