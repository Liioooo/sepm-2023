package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.config.properties.FilesProperties;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventTop10SearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.entity.interfaces.EventWithBoughtCount;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.ArtistService;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import at.ac.tuwien.sepr.groupphase.backend.service.LocationService;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocationService locationService;
    private final ArtistService artistService;

    private final PublicFileService publicFileService;

    private final TicketRepository ticketRepository;

    private final FilesProperties filesProperties;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, LocationService locationService, ArtistService artistService, PublicFileService publicFileService, TicketRepository ticketRepository, FilesProperties filesProperties) {
        this.eventRepository = eventRepository;
        this.locationService = locationService;
        this.artistService = artistService;
        this.publicFileService = publicFileService;
        this.ticketRepository = ticketRepository;
        this.filesProperties = filesProperties;
    }

    @Override
    public Page<Event> getEventsBySearch(EventSearchDto search, Pageable pageable) {
        Page<Event> eventsPage = this.eventRepository.findBySearchCriteria(search, pageable);
        setPublicImagePathForAllEvents(eventsPage.getContent());
        return eventsPage;
    }

    @Override
    public Page<Event> getEventsBySearchWithoutGlobalSearch(EventSearchDto search, Pageable pageable) {
        return this.eventRepository.findBySearchCriteria(search, pageable);
    }

    @Override
    @Transactional
    public void createEvent(EventCreateDto eventCreateDto) {
        PublicFile imageFile = new PublicFile();
        if (eventCreateDto.getImage() != null) {
            imageFile = publicFileService.storeFile(eventCreateDto.getImage());
        }

        Event e = Event.builder()
            .title(eventCreateDto.getTitle())
            .startDate(eventCreateDto.getStartDate())
            .endDate(eventCreateDto.getEndDate())
            .seatPrice(eventCreateDto.getSeatPrice())
            .standingPrice(eventCreateDto.getStandingPrice())
            .image(imageFile)
            .hall(locationService.getHallById(eventCreateDto.getHallId()))
            .artist(artistService.getArtistById(eventCreateDto.getArtistId()))
            .type(eventCreateDto.getType())
            .build();

        eventRepository.save(e);
    }

    @Override
    public Event getEvent(long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("The event was not found"));
        setPublicImagePathForSingleEvent(event);
        return event;
    }

    @Override
    public SeatDto[] getOccupiedSeats(long id) {
        return ticketRepository.findOccupiedSeatsById(id).stream().map((seat -> new SeatDto(seat.getRowNumber(), seat.getSeatNumber()))).toArray(SeatDto[]::new);
    }

    @Override
    public Integer getOccupiedStandings(long id) {
        return ticketRepository.findValidStandingTicketsByEventId(id);
    }

    @Override
    public List<EventWithBoughtCount> getTopTenEvents(EventTop10SearchDto searchDto) {
        YearMonth yearMonth = YearMonth.from(LocalDate.now().plusMonths(searchDto.getMonth()));
        OffsetDateTime startDate = yearMonth.atDay(1).atTime(0, 0, 0).atOffset(ZoneOffset.UTC);
        OffsetDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59).atOffset(ZoneOffset.UTC);
        return eventRepository.findTopTenEvent(startDate, endDate, searchDto.getEventType());
    }

    private void setPublicImagePathForAllEvents(List<Event> eventList) {
        for (Event event : eventList) {
            if (event.getImage() == null) {
                continue;
            }
            setPublicImagePathForSingleEvent(event);
        }
    }

    private void setPublicImagePathForSingleEvent(Event event) {
        if (event.getImage() == null) {
            return;
        }
        String baseUrl = this.filesProperties.getPublicServeUrl().replace("*", "");
        String url = baseUrl + event.getImage().getPath();

        event.getImage().setPublicUrl(url);
    }
}
