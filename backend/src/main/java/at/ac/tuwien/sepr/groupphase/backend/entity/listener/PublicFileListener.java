package at.ac.tuwien.sepr.groupphase.backend.entity.listener;


import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.exception.PublicFileStorageException;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import jakarta.persistence.PostRemove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class PublicFileListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private PublicFileService publicFileService;

    @Autowired
    @Lazy
    public void setPublicFileService(PublicFileService publicFileService) {
        this.publicFileService = publicFileService;
    }

    @PostRemove
    private void afterRemovePublicFile(PublicFile publicFile) {
        try {
            this.publicFileService.deleteFile(publicFile);
        } catch (PublicFileStorageException e) {
            // Transactions should not fail because the file is not present
            LOGGER.warn("%s File Path:%s".formatted(e.getMessage(), publicFile.getPath()));
        }
    }
}
