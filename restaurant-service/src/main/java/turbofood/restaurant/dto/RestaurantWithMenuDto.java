package turbofood.restaurant.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class RestaurantWithMenuDto {
    private UUID id;
    private UUID ownerId;
    private String name;
    private String address;
    private String phone;
    private String website;
    private Boolean acceptingOrders;
    private List<MenuItemDto> menu;
}
