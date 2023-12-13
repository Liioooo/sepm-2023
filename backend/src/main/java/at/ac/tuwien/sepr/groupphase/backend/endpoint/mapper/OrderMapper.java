package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EmbeddedFileDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.OrderListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.EmbeddedFile;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    @Mapping(source = "user", target = "user")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tickets", ignore = true)
    @Mapping(target = "orderDate", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "event.id", source = "orderCreateDto.eventId")
    Order orderCreateDtoToOrder(OrderCreateDto orderCreateDto, ApplicationUser user);

    OrderListDto orderToOrderListDto(Order order);

    OrderDetailDto orderToOrderDetailDto(Order order);

    default EventListDto eventToEventListDto(Event event) {
        return Mappers.getMapper(EventMapper.class).eventToEventListDto(event);
    }

    default EmbeddedFileDto embeddedFileToEmbeddedFileDto(EmbeddedFile embeddedFile) {
        return Mappers.getMapper(EmbeddedFileMapper.class).embeddedFileToEmbeddedFileDto(embeddedFile);
    }

}
