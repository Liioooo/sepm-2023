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

    /**
     * Find all news that HAVE NOT been read by the given user
     * Returned rows are ordered by publish date
     *
     * @param user     the given user
     * @param pageable page
     * @return all news that HAVE NOT been read by the given user
     */
    Page<News> findAllByReadByNotContainsOrderByPublishDateDesc(ApplicationUser user, Pageable pageable);

    /**
     * Find all news that HAVE been read by the given user
     * Returned rows are ordered by publish date
     *
     * @param user     the given user
     * @param pageable page
     * @return all news that HAVE NOT been read by the given user
     */
    Page<News> findAllByReadByContainsOrderByPublishDateDesc(ApplicationUser user, Pageable pageable);

    /**
     * Finds all news by a given title
     *
     * @param title the title
     * @return all news that contain the given title
     */
    Collection<News> findAllByTitleContains(String title);

    @Query("SELECT n FROM News n "
        + "LEFT JOIN n.author u WHERE"
        + "(:#{#search.title} IS NULL OR UPPER(n.title) LIKE UPPER(CONCAT('%', :#{#search.title}, '%'))) AND "
        + "(:#{#search.authorName} IS NULL OR "
        + "(COALESCE(UPPER(n.author.firstName), '') LIKE UPPER(CONCAT('%', :#{#search.authorName}, '%')) OR "
        + "COALESCE(UPPER(n.author.lastName), '') LIKE UPPER(CONCAT('%', :#{#search.authorName}, '%')))) "
        + "ORDER BY n.publishDate DESC")
    Page<News> findNewsBySearchCriteria(@Param("search") NewsSearchDto search, Pageable pageable);
}
