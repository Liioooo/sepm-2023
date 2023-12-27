package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.config.properties.FilesProperties;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.exception.UnauthorizedException;
import at.ac.tuwien.sepr.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final PublicFileService publicFileService;
    private final UserService userService;

    private final FilesProperties filesProperties;

    public NewsServiceImpl(NewsRepository newsRepository, PublicFileService publicFileService, UserService userService, FilesProperties filesProperties) {
        this.newsRepository = newsRepository;
        this.publicFileService = publicFileService;
        this.userService = userService;
        this.filesProperties = filesProperties;
    }

    @Override
    @Transactional
    public News getSingleNews(Long id) {
        News selectedNews = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("No news with id %s found".formatted(id)));

        // Mark the selected news as read for the current user:
        markAsRead(selectedNews);

        setPublicImagePathForSingleNews(selectedNews);

        return selectedNews;
    }

    @Override
    @Transactional
    public Page<News> getAllUnreadNews(Pageable pageable) {
        ApplicationUser user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user is currently logged in"));

        Page<News> newsPage = newsRepository.findAllByReadByNotContainsOrderByPublishDateDesc(user, pageable);
        setPublicImagePathForAllNews(newsPage.getContent());

        return newsPage;
    }

    @Override
    @Transactional
    public Page<News> getAllReadNews(Pageable pageable) {
        ApplicationUser user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user is currently logged in"));

        Page<News> newsPage = newsRepository.findAllByReadByContainsOrderByPublishDateDesc(user, pageable);
        setPublicImagePathForAllNews(newsPage.getContent());

        return newsPage;
    }

    @Override
    @Transactional
    public void createNews(NewsCreateDto newsCreateDto) {
        PublicFile imageFile = new PublicFile();
        if (newsCreateDto.getImage() != null) {
            imageFile = publicFileService.storeFile(newsCreateDto.getImage());
        }

        News n = News.builder()
            .title(newsCreateDto.getTitle())
            .overviewText(newsCreateDto.getOverviewText())
            .text(newsCreateDto.getText())
            .publishDate(OffsetDateTime.now())
            .author(userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user currently logged in")))
            .image(imageFile)
            .build();

        newsRepository.save(n);
    }

    private void setPublicImagePathForAllNews(List<News> newsList) {
        for (News news : newsList) {
            if (news.getImage() == null) {
                continue;
            }
            setPublicImagePathForSingleNews(news);
        }
    }

    private void setPublicImagePathForSingleNews(News news) {
        if (news.getImage() == null) {
            return;
        }
        String baseUrl = this.filesProperties.getPublicServeUrl().replace("*", "");
        String url = baseUrl + news.getImage().getPath();

        news.getImage().setPublicUrl(url);
    }

    private void markAsRead(News news) {
        ApplicationUser user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new NotFoundException("No user is currently logged in"));
        news.getReadBy().add(user);
        newsRepository.save(news);
    }
}
