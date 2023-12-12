package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.SeatDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.TicketRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final PublicFileService publicFileService;

    private final TicketRepository ticketRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, PublicFileService publicFileService, TicketRepository ticketRepository) {
        this.eventRepository = eventRepository;
        this.publicFileService = publicFileService;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Page<Event> getEventsBySearch(EventSearchDto search, Pageable pageable) {
        return this.eventRepository.findBySearchCriteria(search, pageable);
    }

    @Override
    @Transactional
    public void createEvent(EventCreateDto eventCreateDto) {
        PublicFile imageFile = null;
        if (eventCreateDto.getImage() != null) {
            imageFile = this.publicFileService.storeFile(eventCreateDto.getImage());
        }
    }

    @Override
    public Event getEvent(long id) {
        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException("The event was not found"));
    }

    @Override
    public SeatDto[] getOccupiedSeats(long id) {
        return ticketRepository.findOccupiedSeatsById(id).stream().map((seat -> new SeatDto(seat.getRowNumber(), seat.getSeatNumber()))).toArray(SeatDto[]::new);
    }

    @Override
    public Integer getOccupiedStandings(long id) {
        return ticketRepository.findValidStandingTicketsByEventId(id);
    }
}
