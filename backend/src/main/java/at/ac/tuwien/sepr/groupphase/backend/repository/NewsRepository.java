package at.ac.tuwien.sepr.groupphase.backend.repository;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    //Page<News> test();
    Collection<News> findAllByReadByNotContains(ApplicationUser user);

    Collection<News> findAllByTitleContains(String title);

    @Query("SELECT n FROM News n WHERE "
        + "(:#{#search.title} IS NULL OR UPPER(n.title) LIKE UPPER(CONCAT('%', :#{#search.title}, '%'))) AND "
        + "(:#{#search.authorName} IS NULL OR (UPPER(n.author.firstName) LIKE UPPER(CONCAT('%', :#{#search.authorName}, '%')) OR "
        + " UPPER(n.author.lastName) LIKE UPPER(CONCAT('%', :#{#search.authorName}, '%')))) AND "
        + "(:#{#search.publishDate} IS NULL OR n.publishDate = :#{#search.publishDate})")
    Page<News> findNewsBySearchCriteria(@Param("search") NewsSearchDto search, Pageable pageable);
}
