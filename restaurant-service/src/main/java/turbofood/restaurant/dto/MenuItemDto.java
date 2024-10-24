package turbofood.restaurant.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class MenuItemDto {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
}
