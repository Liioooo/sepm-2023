package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventWithBoughtCountDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PublicFileDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.entity.interfaces.EventWithBoughtCount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper
public interface EventMapper {

    @Mapping(source = "image", target = "image", qualifiedByName = "mapImageDto")
    EventListDto eventToEventListDto(Event event);

    EventDetailDto eventToEventDetailDto(Event event);

    List<EventWithBoughtCountDto> eventWithBoughtToEventWithBoughtDtoList(Collection<EventWithBoughtCount> events);

    @Named("mapImageDto")
    static PublicFileDto mapPublicFileDto(PublicFile image) {
        if (image == null) {
            return null;
        }
        return new PublicFileDto(image.getPublicUrl());
    }


}
