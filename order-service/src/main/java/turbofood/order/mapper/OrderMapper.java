package turbofood.order.mapper;

import org.mapstruct.Mapper;

import turbofood.order.dto.CreateOrderRequest;
import turbofood.order.dto.OrderDto;
import turbofood.order.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto orderToOrderDto(Order order);

    Order orderDtoToOrder(OrderDto orderDto);

    Order createOrderRequestToOrder(CreateOrderRequest createOrderRequest);

}
