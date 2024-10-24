package turbofood.order.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import turbofood.order.converter.OrderItemConverter;

@Entity
@Table(name = "en_order")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID restaurantId;

    private UUID customerId;

    private String addressFrom;

    private String addressTo;

    @Convert(converter = OrderItemConverter.class)
    private List<OrderItem> items;

    private Double totalPrice;

    private OrderStatus status;

}
