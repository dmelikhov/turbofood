package turbofood.order.event;

import java.util.UUID;

import lombok.Data;

@Data
public class OrderPreparedEvent {
    private final UUID orderId;
}
