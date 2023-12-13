package at.ac.tuwien.sepr.groupphase.backend.datagenerator;

import at.ac.tuwien.sepr.groupphase.backend.config.properties.FilesProperties;
import at.ac.tuwien.sepr.groupphase.backend.entity.PublicFile;
import at.ac.tuwien.sepr.groupphase.backend.repository.PublicFileRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.PublicFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Profile("generateData")
@Component
public class PublicFileDataGenerator extends DataGenerator<PublicFile> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final PublicFileService publicFileService;
    private final FilesProperties filesProperties;
    private final PublicFileRepository publicFileRepository;
    private final ResourceLoader resourceLoader;

    public PublicFileDataGenerator(PublicFileService publicFileService, FilesProperties filesProperties, PublicFileRepository publicFileRepository,
                                   ResourceLoader resourceLoader) {
        this.publicFileService = publicFileService;
        this.filesProperties = filesProperties;
        this.publicFileRepository = publicFileRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    protected List<PublicFile> generate() {
        /*
        if (publicFileRepository.count() > 0) {
            LOGGER.info("public file data already generated");
            return null;
        }

        final var images = new ArrayList<PublicFile>();

        String[] imageNames = {"news_img_01.jpg", "news_img_02.jpg"};

        for (String imageName : imageNames) {
            copyImageFromResoucesToPublicFiles(imageName);
            images.add(PublicFile.builder()
                .path(imageName)
                .mimeType("image/jpeg")
                .build());
        }

        LOGGER.info("generating public files");
        return publicFileRepository.saveAll(images);
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
        Resource resource = resourceLoader.getResource("classpath:news_images/" + imageName);
        return resource.exists() ? resource.getURL().getPath() : null;
        */
        return null;
    }


}
