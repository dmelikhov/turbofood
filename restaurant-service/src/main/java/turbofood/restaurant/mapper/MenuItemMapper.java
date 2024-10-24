package turbofood.restaurant.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import turbofood.restaurant.dto.MenuItemDto;
import turbofood.restaurant.entity.MenuItem;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    MenuItemDto menuItemToMenuItemDto(MenuItem menuItem);

    @Mapping(target = "restaurant", ignore = true)
    MenuItem menuItemDtoToMenuItem(MenuItemDto menuItemDto);

}
