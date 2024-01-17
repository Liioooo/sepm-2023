package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PublicFileDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface OrderMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "orderDate", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "event.id", source = "orderCreateDto.eventId")
    Order orderCreateDtoToOrder(OrderCreateDto orderCreateDto, ApplicationUser user);

    @Mapping(source = "event.image", target = "event.image", qualifiedByName = "mapImageDto")
    OrderListDto orderToOrderListDto(Order order);

    OrderDetailDto orderToOrderDetailDto(Order order);

    @Named("mapImageDto")
    static PublicFileDto mapPublicFileDto(PublicFile image) {
        if (image == null) {
            return null;
        }
        return new PublicFileDto(image.getPublicUrl());
    }

}
