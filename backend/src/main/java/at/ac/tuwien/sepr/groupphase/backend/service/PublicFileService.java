package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import org.springframework.web.multipart.MultipartFile;

public interface PublicFileService {

    /**
     * Stores a file on disk.
     *
     * @param file the file to store
     * @return Entity of the saved file
     */
    PublicFile storeFile(MultipartFile file);

    /**
     * Deletes a file on disk.
     *
     * @param file the file to delete
     */
    void deleteFile(PublicFile file);
}
