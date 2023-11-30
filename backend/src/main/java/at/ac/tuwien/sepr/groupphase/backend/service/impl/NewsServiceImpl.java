package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NewsServiceImpl implements NewsService {

    private final PublicFileService publicFileService;
    private final NewsRepository newsRepository;
    private final UserService userService;

    public NewsServiceImpl(PublicFileService publicFileService, NewsRepository newsRepository, UserService userService) {
        this.publicFileService = publicFileService;
        this.newsRepository = newsRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void createNews(NewsCreateDto newsCreateDto) {
        PublicFile file = publicFileService.storeFile(newsCreateDto.getImage());

        News n = News.builder()
            .author(userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new NotFoundException("No user currently logged in")))
            .image(file)
            .build();

        newsRepository.save(n);
    }
}
