package turbofood.delivery.dto;

import java.util.UUID;

import lombok.Data;
import turbofood.delivery.entity.DeliveryStatus;

@Data
public class DeliveryAssignmentDto {
    private UUID id;
    private UUID orderId;
    private UUID courierId;
    private DeliveryStatus status;
}
