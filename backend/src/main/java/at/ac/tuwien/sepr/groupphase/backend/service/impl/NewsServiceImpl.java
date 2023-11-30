package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsListDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.NewsMapper;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.exception.UnauthorizedException;
import at.ac.tuwien.sepr.groupphase.backend.repository.NewsRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.NewsService;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final PublicFileService publicFileService;
    private final UserService userService;
    private final NewsMapper newsMapper;

    public NewsServiceImpl(NewsRepository newsRepository, PublicFileService publicFileService, UserService userService,
                           NewsMapper newsMapper) {
        this.newsRepository = newsRepository;
        this.publicFileService = publicFileService;
        this.userService = userService;
        this.newsMapper = newsMapper;
    }

    @Override
    @Transactional
    public NewsDetailDto getSingleNews(Long id) {
        News selectedNews = newsRepository.findById(id).orElseThrow(() -> new NotFoundException("No news with id %s found".formatted(id)));

        // Mark the selected news as read for the current user:
        markAsRead(selectedNews);

        return newsMapper.toNewsDetailDto(selectedNews);
    }

    @Override
    public List<NewsListDto> getAllUnreadNews() {
        ApplicationUser user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user is currently logged in"));
        return newsRepository.findAllByReadByNotContains(user).stream().map(newsMapper::toNewsListDto).toList();
    }


    @Override
    public List<NewsListDto> getAllReadNews() {
        ApplicationUser user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user is currently logged in"));
        return user.getReadNews().stream().map(newsMapper::toNewsListDto).toList();
    }

    @Override
    @Transactional
    public void createNews(NewsCreateDto newsCreateDto) {
        PublicFile file = publicFileService.storeFile(newsCreateDto.getImage());

        News n = News.builder()
            .author(userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new UnauthorizedException("No user currently logged in")))
            .image(file)
            .build();

        newsRepository.save(n);
    }

    private void markAsRead(News news) {
        ApplicationUser user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new NotFoundException("No user is currently logged in"));
        news.getReadBy().add(user);
        newsRepository.save(news);
    }


}
