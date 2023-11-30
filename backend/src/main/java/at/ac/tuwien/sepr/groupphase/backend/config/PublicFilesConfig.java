package at.ac.tuwien.sepr.groupphase.backend.config;

import at.ac.tuwien.sepr.groupphase.backend.config.properties.FilesProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PublicFilesConfig implements WebMvcConfigurer {

    private final FilesProperties filesProperties;

    PublicFilesConfig(FilesProperties filesProperties) {
        this.filesProperties = filesProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(filesProperties.getPublicServeUrl())
            .addResourceLocations(new PathResource(filesProperties.getPublicDiskPath()));
    }
}
