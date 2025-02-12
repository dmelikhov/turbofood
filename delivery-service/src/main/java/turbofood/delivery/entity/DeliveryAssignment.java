package turbofood.delivery.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DeliveryAssignment {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID orderId;

    private UUID courierId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

}
