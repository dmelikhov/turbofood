package turbofood.order.event;

import java.util.UUID;

import lombok.Data;

@Data
public class CourierArrivedEvent {
    private final UUID orderId;
}
