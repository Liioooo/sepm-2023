package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    void createNews(NewsCreateDto eventCreateDto);

    NewsDetailDto getSingleNews(Long id);

    PageDto<NewsListDto> getAllUnreadNews(Pageable pageable);

    PageDto<NewsListDto> getAllReadNews(Pageable pageable);
}
