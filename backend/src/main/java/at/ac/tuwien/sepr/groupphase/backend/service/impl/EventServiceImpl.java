package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.EventMapper;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.PageMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.EventService;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final PublicFileService publicFileService;

    private final EventMapper eventMapper;

    private final PageMapper pageMapper;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, PublicFileService publicFileService, EventMapper eventMapper, PageMapper pageMapper) {
        this.eventRepository = eventRepository;
        this.publicFileService = publicFileService;
        this.eventMapper = eventMapper;
        this.pageMapper = pageMapper;
    }

    @Override
    public PageDto<EventListDto> getEventsBySearch(EventSearchDto search, Pageable pageable) {
        return this.pageMapper.toPageDtoListMapper(
            this.eventRepository.findBySearchCriteria(search, pageable), this.eventMapper::eventCollectionToEventListDtoCollection
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
