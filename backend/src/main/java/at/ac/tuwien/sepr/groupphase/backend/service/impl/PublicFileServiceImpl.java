package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.config.properties.FilesProperties;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.exception.PublicFileStorageException;
import at.ac.tuwien.sepr.groupphase.backend.repository.PublicFileRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class PublicFileServiceImpl implements PublicFileService {

    private final FilesProperties filesProperties;

    private final PublicFileRepository publicFileRepository;

    @Autowired
    PublicFileServiceImpl(FilesProperties filesProperties, PublicFileRepository publicFileRepository) {
        this.filesProperties = filesProperties;
        this.publicFileRepository = publicFileRepository;
    }

    @Override
    public PublicFile storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new PublicFileStorageException("Failed to store empty file.");
        }

        String fileName = UUID.randomUUID() + getExtensionFromMimeType(file.getContentType());

        var filePath = this.filesProperties.getPublicDiskPath().resolve(fileName);

        if (!filePath.getParent().equals(this.filesProperties.getPublicDiskPath())) {
            throw new PublicFileStorageException("Cannot store file outside current directory.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new PublicFileStorageException("Failed to store file.", e);
        }

        PublicFile publicFile = PublicFile.builder()
            .path(fileName)
            .mimeType(file.getContentType())
            .build();

        return publicFileRepository.save(publicFile);
    }

    @Override
    public void deleteFile(PublicFile file) {
        var filePath = this.filesProperties.getPublicDiskPath().resolve(file.getPath());
        try {
            Files.delete(filePath);
        } catch (IOException e) {
            throw new PublicFileStorageException("Failed to delete stored file.");
        }
    }

    private String getExtensionFromMimeType(String mimeType) {
        return switch (mimeType) {
            case MimeTypeUtils.IMAGE_JPEG_VALUE -> ".jpg";
            case MimeTypeUtils.IMAGE_PNG_VALUE -> ".png";
            case MimeTypeUtils.IMAGE_GIF_VALUE -> ".gif";
            default -> "";
        };
    }
}
