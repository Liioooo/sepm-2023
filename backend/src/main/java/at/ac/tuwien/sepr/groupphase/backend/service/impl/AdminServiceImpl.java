package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.EventRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final EventRepository eventRepository;
    private final NewsRepository newsRepository;
    private final ApplicationUserRepository applicationUserRepository;

    public AdminServiceImpl(EventRepository eventRepository, NewsRepository newsRepository, ApplicationUserRepository applicationUserRepository) {
        this.eventRepository = eventRepository;
        this.newsRepository = newsRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public Page<ApplicationUser> getUsersBySearch(UserSearchDto search, Pageable pageable) {
        return this.applicationUserRepository.findUserBySearchCriteria(search, pageable);
    }

    @Override
    public Page<Event> getEventsBySearch(EventSearchDto search, Pageable pageable) {
        return this.eventRepository.findEventBySearchCriteria(search, pageable);
    }

    @Override
    public Page<News> getNewsBySearch(NewsSearchDto search, Pageable pageable) {
        return this.newsRepository.findNewsBySearchCriteria(search, pageable);
    }
}
