package at.ac.tuwien.sepr.groupphase.backend.repository;

import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    Page<News> findAllByReadByNotContainsOrderByPublishDateDesc(ApplicationUser user, Pageable pageable);

    Page<News> findAllByReadByContainsOrderByPublishDateDesc(ApplicationUser user, Pageable pageable);

    Collection<News> findAllByTitleContains(String title);
}
