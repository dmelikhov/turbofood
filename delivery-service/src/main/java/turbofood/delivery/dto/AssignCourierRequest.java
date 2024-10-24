package turbofood.delivery.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class AssignCourierRequest {
    private UUID orderId;
}
