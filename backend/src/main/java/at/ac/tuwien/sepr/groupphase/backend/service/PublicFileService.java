package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import org.springframework.web.multipart.MultipartFile;

public interface PublicFileService {

    /**
     * Stores a file on disk.
     *
     * @param file the file to store
     * @return Entity of the saved file
     * @throws at.ac.tuwien.sepr.groupphase.backend.exception.PublicFileStorageException if the file cannot be stored for any reason
     */
    PublicFile storeFile(MultipartFile file);

    /**
     * Deletes a file on disk.
     * Should never be called directly!
     * It is called automatically by the EntityListener when the PublicFile entity associated with this file is deleted.
     *
     * @param file the file to delete
     */
    void deleteFile(PublicFile file);
}
