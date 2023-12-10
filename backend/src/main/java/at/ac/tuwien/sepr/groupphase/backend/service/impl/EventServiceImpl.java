package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.enums.EventType;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final PublicFileService publicFileService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, PublicFileService publicFileService) {
        this.eventRepository = eventRepository;
        this.publicFileService = publicFileService;
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

    public Event[] getTopTenEvents(int month, EventType type) {

        YearMonth yearMonth = YearMonth.from(LocalDate.now().plusMonths(month));
        LocalDateTime startDate = yearMonth.atDay(1).atTime(0, 0, 0);
        LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return eventRepository.findTopTenEvent(startDate, endDate, type);
    }
}



