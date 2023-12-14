package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventWithBoughtCountDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.interfaces.EventWithBoughtCount;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface EventMapper {

    EventListDto eventToEventListDto(Event event);

    EventDetailDto eventToEventDetailDto(Event event);

    List<EventWithBoughtCountDto> eventWithBoughtToEventWithBoughtDtoList(Collection<EventWithBoughtCount> events);


}
