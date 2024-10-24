package turbofood.delivery.event;

import java.util.UUID;

import lombok.Data;

@Data
public class CourierAssignedEvent {
    private final UUID orderId;
}
