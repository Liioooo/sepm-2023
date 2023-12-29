package at.ac.tuwien.sepr.groupphase.backend.entity.listener;


import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import at.ac.tuwien.sepr.groupphase.backend.repository.ApplicationUserRepository;
import at.ac.tuwien.sepr.groupphase.backend.repository.NewsRepository;
import jakarta.persistence.PreRemove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class ApplicationUserListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    private ApplicationUserRepository userRepository;

    private NewsRepository newsRepository;

    @Autowired
    @Lazy
    public void setUserRepository(ApplicationUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    @Lazy
    public void setNewsRepository(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @PreRemove
    public void preRemove(ApplicationUser user) {
        // Remove the author of these users news
        user.getAuthoredNews().forEach(news -> news.setAuthor(null));

        for (News readNews : user.getReadNews()) {
            readNews.getReadBy().remove(user);
            newsRepository.save(readNews);
        }

        newsRepository.flush();
        userRepository.saveAndFlush(user);
    }
}
