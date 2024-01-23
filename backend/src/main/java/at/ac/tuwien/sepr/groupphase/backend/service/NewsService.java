package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {

    /**
     * Creates a new news article based on the provided NewsCreateDto.
     *
     * @param newsCreateDto The data transfer object containing information for creating the news article.
     */
    void createNews(NewsCreateDto newsCreateDto);

    /**
     * Retrieves a single news article based on its unique identifier.
     *
     * @param id The unique identifier of the news article.
     * @return The News object representing the single news article.
     */
    News getSingleNews(Long id);

    /**
     * Retrieves a paginated list of all unread news articles.
     *
     * @param pageable The pagination information, including page number, size, and sorting.
     * @return A Page containing News objects representing unread news articles.
     */
    Page<News> getAllUnreadNews(Pageable pageable);

    /**
     * Retrieves a paginated list of all read news articles.
     *
     * @param pageable The pagination information, including page number, size, and sorting.
     * @return A Page containing News objects representing read news articles.
     */
    Page<News> getAllReadNews(Pageable pageable);

    /**
     * Find news by search criteria.
     *
     * @param search the search criteria
     * @return the collection of news
     */
    Page<News> getNewsBySearch(NewsSearchDto search, Pageable pageable);
}
