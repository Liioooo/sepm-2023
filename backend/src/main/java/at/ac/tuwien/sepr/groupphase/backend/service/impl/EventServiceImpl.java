package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.Collection;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final EventRepository eventRepository;

    private final PublicFileService publicFileService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, PublicFileService publicFileService) {
        this.eventRepository = eventRepository;
        this.publicFileService = publicFileService;
    }

    @Override
    public Collection<Event> getEventsBySearch(EventSearchDto search) {
        return eventRepository.findBySearchCriteria(search);
    }

    @Override
    @Transactional
    public void createEvent(EventCreateDto eventCreateDto) {
        PublicFile imageFile = null;
        if (eventCreateDto.getImage() != null) {
            imageFile = this.publicFileService.storeFile(eventCreateDto.getImage());
        }
    }
}
