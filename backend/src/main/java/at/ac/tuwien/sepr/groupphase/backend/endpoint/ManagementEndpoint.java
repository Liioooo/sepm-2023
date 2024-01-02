package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ArtistDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListManagementDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.ApplicationUserMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.ArtistMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.LocationMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.PageMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import at.ac.tuwien.sepr.groupphase.backend.service.LocationService;
import at.ac.tuwien.sepr.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "api/v1/management")
public class ManagementEndpoint {
    private final NewsService newsService;
    private final EventService eventService;
    private final UserService userService;
    private final LocationService locationService;
    private final ArtistService artistService;
    private final ApplicationUserMapper applicationUserMapper;
    private final EventMapper eventMapper;
    private final NewsMapper newsMapper;
    private final PageMapper pageMapper;
    private final ArtistMapper artistMapper;
    private final LocationMapper locationMapper;

    public ManagementEndpoint(NewsService newsService, EventService eventService, UserService userService, LocationService locationService, ArtistService artistService, ApplicationUserMapper applicationUserMapper,
                              EventMapper eventMapper, NewsMapper newsMapper, PageMapper pageMapper, ArtistMapper artistMapper, LocationMapper locationMapper) {
        this.newsService = newsService;
        this.eventService = eventService;
        this.userService = userService;
        this.locationService = locationService;
        this.artistService = artistService;
        this.applicationUserMapper = applicationUserMapper;
        this.eventMapper = eventMapper;
        this.newsMapper = newsMapper;
        this.pageMapper = pageMapper;
        this.artistMapper = artistMapper;
        this.locationMapper = locationMapper;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping({"events"})
    @Operation(summary = "Get users, optionally filter by search criteria")
    public PageDto<EventListDto> getEvents(EventSearchDto search, Pageable pageable) {
        return this.pageMapper.toPageDto(
            eventService.getEventsBySearchWithoutGlobalSearch(search, pageable), this.eventMapper::eventToEventListDto
        );
    }

    @Secured("ROLE_ADMIN")
    @GetMapping({"users"})
    @Operation(summary = "Get users, optionally filter by search criteria")
    public PageDto<UserListDto> getUsers(UserSearchDto search, Pageable pageable) {
        return this.pageMapper.toPageDto(
            userService.getUsersBySearch(search, pageable), this.applicationUserMapper::toUserListDto
        );
    }

    @Secured("ROLE_ADMIN")
    @GetMapping({"news"})
    @Operation(summary = "Get users, optionally filter by search criteria")
    public PageDto<NewsListManagementDto> getNews(NewsSearchDto search, Pageable pageable) {
        return this.pageMapper.toPageDto(
            newsService.getNewsBySearch(search, pageable), this.newsMapper::toNewsListManagementDto
        );
    }

    @Secured("ROLE_ADMIN")
    @GetMapping({"artists"})
    @Operation(summary = "Get all artists")
    public List<ArtistDetailDto> getAllArtists() {
        return artistMapper.artistCollectionToArtistDetailDtoList(artistService.getAllArtists());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping({"halls"})
    @Operation(summary = "Get all halls")
    public List<HallDetailDto> getAllHalls() {
        return locationMapper.hallCollectionToHallDetailDtoList(locationService.getAllHalls());
    }
}
