package at.ac.tuwien.sepr.groupphase.backend.entity.listener;


import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import jakarta.persistence.PreRemove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class ApplicationUserListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @PreRemove
    public void preRemove(ApplicationUser user) {
        user.getAuthoredNews().forEach(news -> news.setAuthor(null));
    }
}
