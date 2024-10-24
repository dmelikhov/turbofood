package turbofood.order.event;

import java.util.UUID;

import lombok.Data;

@Data
public class PaymentReceivedEvent {
    private final UUID orderId;
}
