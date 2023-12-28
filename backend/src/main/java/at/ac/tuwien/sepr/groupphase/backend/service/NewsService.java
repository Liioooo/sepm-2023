package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    void createNews(NewsCreateDto eventCreateDto);

    News getSingleNews(Long id);

    Page<News> getAllUnreadNews(Pageable pageable);

    Page<News> getAllReadNews(Pageable pageable);
}
