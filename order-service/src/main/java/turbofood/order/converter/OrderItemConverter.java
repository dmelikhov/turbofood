package turbofood.order.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import turbofood.order.entity.OrderItem;

@Converter
public class OrderItemConverter implements AttributeConverter<OrderItem, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(OrderItem attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderItem convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, OrderItem.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
