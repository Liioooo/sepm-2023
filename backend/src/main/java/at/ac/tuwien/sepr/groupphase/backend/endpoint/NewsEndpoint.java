package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.PageDto;
import at.ac.tuwien.sepr.groupphase.backend.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/news")
public class NewsEndpoint {

    private final NewsService newsService;


    public NewsEndpoint(NewsService newsService) {
        this.newsService = newsService;
    }

    @Secured("ROLE_USER")
    @GetMapping({"{id}"})
    @Operation(summary = "Get a single News")
    public NewsDetailDto getSingleNews(@PathVariable long id) {
        return newsService.getSingleNews(id);
    }

    @Secured("ROLE_USER")
    @GetMapping({"unread"})
    @Operation(summary = "Get a all non read News")
    public PageDto<NewsListDto> getAllUnReadNews(Pageable pageable) {
        return newsService.getAllUnreadNews(pageable);
    }

    @Secured("ROLE_USER")
    @GetMapping({"read"})
    @Operation(summary = "Get a all non read News")
    public PageDto<NewsListDto> getAllReadNews(Pageable pageable) {
        return newsService.getAllReadNews(pageable);
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    @Operation(summary = "Creates a new news-article")
    public void createNews(@Valid @ModelAttribute NewsCreateDto newsCreateDto) {
        newsService.createNews(newsCreateDto);
    }


}
