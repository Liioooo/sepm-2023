package at.ac.tuwien.sepr.groupphase.backend.repository;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE "
        + "(:#{#search.artist} IS NULL OR ("
        + "  UPPER(e.artist.fictionalName) LIKE UPPER(CONCAT('%', :#{#search.artist}, '%')) OR "
        + "  UPPER(e.artist.firstname) LIKE UPPER(CONCAT('%', :#{#search.artist}, '%')) OR "
        + "  UPPER(e.artist.lastname) LIKE UPPER(CONCAT('%', :#{#search.artist}, '%')))) AND "
        + "(:#{#search.title} IS NULL OR UPPER(e.title) LIKE UPPER(CONCAT('%', :#{#search.title}, '%'))) AND "
        + "(:#{#search.location} IS NULL OR ("
        + "  UPPER(e.hall.location.title) LIKE UPPER(CONCAT('%', :#{#search.location}, '%')) OR "
        + "  UPPER(e.hall.location.address) LIKE UPPER(CONCAT('%', :#{#search.location}, '%')) OR "
        + "  UPPER(e.hall.location.city) LIKE UPPER(CONCAT('%', :#{#search.location}, '%')) OR "
        + "  UPPER(e.hall.location.postalCode) LIKE UPPER(CONCAT('%', :#{#search.location}, '%')) OR "
        + "  UPPER(e.hall.location.country) LIKE UPPER(CONCAT('%', :#{#search.location}, '%')))) AND "
        + "(:#{#search.timeEnd} IS NULL OR e.startDate <= :#{#search.timeEnd}) AND "
        + "(:#{#search.timeStart} IS NULL OR e.endDate >= :#{#search.timeStart}) AND "
        + "(:#{#search.priceMax} IS NULL OR ("
        + "  e.standingPrice <= :#{#search.priceMax} OR "
        + "  e.seatPrice <= :#{#search.priceMax})) AND "
        + "(:#{#search.type?.name()} IS NULL OR e.type = :#{#search.type}) AND"
        + "(:#{#search.duration} IS NULL OR ("
        + "  e.endDate - e.startDate >= :#{#search.getDurationAsDuration()?.minusHours(1)} AND"
        + "  e.endDate - e.startDate <= :#{#search.getDurationAsDuration()?.plusHours(1)})) AND"
        + "(:#{#search.search} IS NULL OR ("
        + "  UPPER(e.artist.fictionalName) LIKE UPPER(CONCAT('%', :#{#search.search}, '%')) OR "
        + "  UPPER(e.artist.firstname) LIKE UPPER(CONCAT('%', :#{#search.search}, '%')) OR "
        + "  UPPER(e.artist.lastname) LIKE UPPER(CONCAT('%', :#{#search.search}, '%')) OR "
        + "  UPPER(e.title) LIKE UPPER(CONCAT('%', :#{#search.search}, '%')) OR "
        + "  UPPER(e.hall.location.title) LIKE UPPER(CONCAT('%', :#{#search.search}, '%')) OR "
        + "  UPPER(e.hall.location.address) LIKE UPPER(CONCAT('%', :#{#search.search}, '%')) OR "
        + "  UPPER(e.hall.location.city) LIKE UPPER(CONCAT('%', :#{#search.search}, '%')) OR "
        + "  UPPER(e.hall.location.postalCode) LIKE UPPER(CONCAT('%', :#{#search.search}, '%')) OR "
        + "  UPPER(e.hall.location.country) LIKE UPPER(CONCAT('%', :#{#search.search}, '%'))))"
    )
    Collection<Event> findBySearchCriteria(@Param("search") EventSearchDto search);
}
