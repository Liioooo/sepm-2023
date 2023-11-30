package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsCreateDto;

public interface NewsService {
    void createNews(NewsCreateDto eventCreateDto);
}
