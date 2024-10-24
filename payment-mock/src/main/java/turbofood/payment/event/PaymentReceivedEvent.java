package turbofood.payment.event;

import java.util.UUID;

public class PaymentReceivedEvent {
    private final UUID orderId;

    public PaymentReceivedEvent(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }
}
