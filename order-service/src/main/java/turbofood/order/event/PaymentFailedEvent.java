package turbofood.order.event;

import java.util.UUID;

import lombok.Data;

@Data
public class PaymentFailedEvent {
    private final UUID orderId;
}
