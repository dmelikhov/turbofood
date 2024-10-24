package turbofood.order.event;

import java.util.UUID;

import lombok.Data;

@Data
public class CourierAssignedEvent {
    private final UUID orderId;
}
