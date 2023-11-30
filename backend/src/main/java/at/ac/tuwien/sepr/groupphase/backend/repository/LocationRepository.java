package at.ac.tuwien.sepr.groupphase.backend.repository;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.LocationSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("SELECT l FROM Location l WHERE "
        + "(:#{#search.title} IS NULL OR UPPER(l.title) LIKE UPPER(CONCAT('%', :#{#search.title}, '%'))) AND "
        + "(:#{#search.address} IS NULL OR UPPER(l.address) LIKE UPPER(CONCAT('%', :#{#search.address}, '%'))) AND "
        + "(:#{#search.postalCode} IS NULL OR UPPER(l.postalCode) LIKE UPPER(CONCAT('%', :#{#search.postalCode}, '%'))) AND "
        + "(:#{#search.city} IS NULL OR UPPER(l.city) LIKE UPPER(CONCAT('%', :#{#search.city}, '%'))) AND "
        + "(:#{#search.country} IS NULL OR UPPER(l.country) LIKE UPPER(CONCAT('%', :#{#search.country}, '%')))"
    )
    Collection<Location> findBySearchCriteria(@Param("search") LocationSearchDto search);

}
