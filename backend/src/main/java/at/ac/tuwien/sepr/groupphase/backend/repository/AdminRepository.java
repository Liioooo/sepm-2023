package at.ac.tuwien.sepr.groupphase.backend.repository;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Event, Long> {

    @Query("SELECT u FROM ApplicationUser u WHERE "
        + "(:#{#search.firstName} IS NULL OR UPPER(u.firstName) LIKE UPPER(CONCAT('%', :#{#search.firstName}, '%'))) AND "
        + "(:#{#search.lastName} IS NULL OR UPPER(u.lastName) LIKE UPPER(CONCAT('%', :#{#search.lastName}, '%'))) AND "
        + "(:#{#search.email} IS NULL OR UPPER(u.email) LIKE UPPER(CONCAT('%', :#{#search.email}, '%')))")
    Page<ApplicationUser> findUserBySearchCriteria(@Param("search") UserSearchDto search, Pageable pageable);

    @Query("SELECT e FROM Event e WHERE "
        + "(:#{#search.artist} IS NULL OR ("
        + "  UPPER(e.artist.fictionalName) LIKE UPPER(CONCAT('%', :#{#search.artist}, '%')) OR "
        + "  UPPER(e.artist.firstname) LIKE UPPER(CONCAT('%', :#{#search.artist}, '%')) OR "
        + "  UPPER(e.artist.lastname) LIKE UPPER(CONCAT('%', :#{#search.artist}, '%')))) AND "
        + "(:#{#search.title} IS NULL OR UPPER(e.title) LIKE UPPER(CONCAT('%', :#{#search.title}, '%'))) AND "
        + "(:#{#search.locationId} IS NULL OR :#{#search.locationId} = e.hall.location.id) AND "
        + "(:#{#search.timeEnd} IS NULL OR e.startDate <= :#{#search.timeEnd}) AND "
        + "(:#{#search.timeStart} IS NULL OR e.endDate >= :#{#search.timeStart}) AND "
        + "(:#{#search.priceMax} IS NULL OR ("
        + "  e.standingPrice <= :#{#search.priceMax} OR "
        + "  e.seatPrice <= :#{#search.priceMax})) AND "
        + "(:#{#search.type?.name()} IS NULL OR e.type = :#{#search.type}) AND"
        + "(:#{#search.duration} IS NULL OR ("
        + "  e.endDate - e.startDate >= :#{#search.getDurationAsDuration()?.minusHours(1)} AND"
        + "  e.endDate - e.startDate <= :#{#search.getDurationAsDuration()?.plusHours(1)}))")
    Page<Event> findEventBySearchCriteria(@Param("search") EventSearchDto search, Pageable pageable);

    @Query("SELECT n FROM News n WHERE "
        + "(:#{#search.title} IS NULL OR UPPER(n.title) LIKE UPPER(CONCAT('%', :#{#search.title}, '%'))) AND "
        + "(:#{#search.authorName} IS NULL OR (UPPER(n.author.firstName) LIKE UPPER(CONCAT('%', :#{#search.authorName}, '%')) OR "
        + " UPPER(n.author.lastName) LIKE UPPER(CONCAT('%', :#{#search.authorName}, '%')))) AND "
        + "(:#{#search.publishDate} IS NULL OR n.publishDate = :#{#search.publishDate})")
    Page<News> findNewsBySearchCriteria(@Param("search") NewsSearchDto search, Pageable pageable);

}
