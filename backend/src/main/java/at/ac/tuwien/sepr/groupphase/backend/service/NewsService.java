package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListDto;

import java.util.List;

public interface NewsService {
    void createNews(NewsCreateDto eventCreateDto);

    NewsDetailDto getSingleNews(Long id);

    List<NewsListDto> getAllNonReadNews();
}
