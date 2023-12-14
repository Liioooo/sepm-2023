package at.ac.tuwien.sepr.groupphase.backend.repository;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findUserByEmail(String email);

    @Query("SELECT u FROM ApplicationUser u WHERE "
        + "(:#{#search.firstName} IS NULL OR UPPER(u.firstName) LIKE UPPER(CONCAT('%', :#{#search.firstName}, '%'))) AND "
        + "(:#{#search.lastName} IS NULL OR UPPER(u.lastName) LIKE UPPER(CONCAT('%', :#{#search.lastName}, '%'))) AND "
        + "(:#{#search.email} IS NULL OR UPPER(u.email) LIKE UPPER(CONCAT('%', :#{#search.email}, '%')))")
    Page<ApplicationUser> findUserBySearchCriteria(@Param("search") UserSearchDto search, Pageable pageable);

}
