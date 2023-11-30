package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/v1/news")
public class NewsEndpoint {

    private final NewsService newsService;

    @Autowired
    public NewsEndpoint(NewsService newsService) {
        this.newsService = newsService;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    @Operation(summary = "Creates a new event")
    public void createEvent(@Valid @ModelAttribute NewsCreateDto eventCreateDto) {
        newsService.createNews(eventCreateDto);
        // Just for testing file upload...
    }
}
