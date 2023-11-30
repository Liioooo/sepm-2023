package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final PublicFileService publicFileService;

    private final EventMapper eventMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, PublicFileService publicFileService, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.publicFileService = publicFileService;
        this.eventMapper = eventMapper;
    }

    @Override
    public List<EventListDto> getEventsBySearch(EventSearchDto search) {
        return this.eventMapper.eventCollectionToEventListDtoCollection(
            eventRepository.findBySearchCriteria(search)
        );
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
