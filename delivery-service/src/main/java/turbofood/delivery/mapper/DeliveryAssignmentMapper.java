package turbofood.delivery.mapper;

import org.mapstruct.Mapper;

import turbofood.delivery.dto.DeliveryAssignmentDto;
import turbofood.delivery.entity.DeliveryAssignment;

@Mapper(componentModel = "spring")
public interface DeliveryAssignmentMapper {

    DeliveryAssignmentDto deliveryAssignmentToDeliveryAssignmentDto(DeliveryAssignment deliveryAssignment);

    DeliveryAssignment deliveryAssignmentDtoToDeliveryAssignment(DeliveryAssignmentDto deliveryAssignmentDto);

}
