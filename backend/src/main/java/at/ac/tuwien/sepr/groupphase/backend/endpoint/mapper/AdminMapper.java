package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface AdminMapper {

    List<UserListDto> userCollectionToUserListDtoCollection(Collection<ApplicationUser> users);

    List<EventListDto> eventCollectionToEventListDtoCollection(Collection<Event> events);

    List<NewsListDto> newsCollectionToNewsListDtoCollection(Collection<News> news);


}
