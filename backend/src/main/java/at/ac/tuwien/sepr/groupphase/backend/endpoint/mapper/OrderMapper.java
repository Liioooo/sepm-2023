package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "orderDate", expression = "java(java.time.OffsetDateTime.now())")
    Order orderCreateDtoToOrder(OrderCreateDto orderCreateDto, ApplicationUser user);
}
