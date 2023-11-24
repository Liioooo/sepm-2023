package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import org.mapstruct.Mapper;

@Mapper
public interface EventMapper {

    EventListDto toEventListDto(Event event);

}
