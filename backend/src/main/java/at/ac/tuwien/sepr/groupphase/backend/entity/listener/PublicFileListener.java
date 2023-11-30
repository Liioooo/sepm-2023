package at.ac.tuwien.sepr.groupphase.backend.entity.listener;


import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import jakarta.persistence.PostRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class PublicFileListener {

    private PublicFileService publicFileService;

    @Autowired
    @Lazy
    public void setPublicFileService(PublicFileService publicFileService) {
        this.publicFileService = publicFileService;
    }

    @PostRemove
    private void afterRemovePublicFile(PublicFile publicFile) {
        this.publicFileService.deleteFile(publicFile);
    }
}
