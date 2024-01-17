package at.ac.tuwien.sepr.groupphase.backend.datagenerator.test;

import at.ac.tuwien.sepr.groupphase.backend.config.properties.FilesProperties;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Profile("generateTestData")
@Component
public class PublicFileDataGenerator extends DataGenerator<PublicFile> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final PublicFileService publicFileService;
    private final FilesProperties filesProperties;
    private final ResourceLoader resourceLoader;

    public PublicFileDataGenerator(PublicFileService publicFileService, FilesProperties filesProperties, ResourceLoader resourceLoader) {
        this.publicFileService = publicFileService;
        this.filesProperties = filesProperties;
        this.resourceLoader = resourceLoader;
    }

    @Override
    protected List<PublicFile> generate() {
        final List<PublicFile> images = new ArrayList<PublicFile>();

        String[] sourceImages = new String[] {"news_img_A.jpg", "news_img_B.jpg", "event_1.jpg", "event_2.jpg", "event_3.jpg"};


        for (String imageName : sourceImages) {
            copyImageFromResoucesToPublicFiles(imageName);
            images.add(PublicFile.builder()
                .path(imageName)
                .publicUrl("/public-files/" + imageName)
                .mimeType("image/jpeg")
                .build());
        }

        LOGGER.info("generating public files");

        return images;
    }

    public String copyImageFromResoucesToPublicFiles(String imageName) {
        try {
            String sourcePath = getImagePath(imageName);
            FileInputStream inputStream = new FileInputStream(sourcePath);
            Path destinationfilePath = this.filesProperties.getPublicDiskPath().resolve(imageName);

            Files.copy(inputStream, destinationfilePath, StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageName;
    }

    public String getImagePath(String imageName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:test_images/" + imageName);
        return resource.exists() ? resource.getURL().getPath() : null;
    }


}
