package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {


    /**
     * Find users that fit the search criteria
     *
     * @param search the search criteria
     * @return the collection of users
     */
    Page<ApplicationUser> getUsersBySearch(UserSearchDto search, Pageable pageable);

    /**
     * Find events by search criteria
     * @param search the search criteria
     * @return the collection of events
     */
    Page<Event> getEventsBySearch(EventSearchDto search, Pageable pageable);

    /**
     * Find news by search criteria
     * @param search the search criteria
     * @return the collection of news
     */
    Page<News> getNewsBySearch(NewsSearchDto search, Pageable pageable);
}
