package at.ac.tuwien.sepr.groupphase.backend.config.properties;

import at.ac.tuwien.sepr.groupphase.backend.config.FilesPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This configuration class offers all necessary files serving properties in an immutable manner.
 */
@Component
public class FilesProperties {

    private final FilesPropertiesConfig.FilesConfig config;
    private final Path publicDiskPath;

    @Autowired
    public FilesProperties(FilesPropertiesConfig.FilesConfig config) {
        this.config = config;

        this.publicDiskPath = Paths.get("").resolve(config.getPublicDiskPath()).normalize().toAbsolutePath();
    }

    public String getPublicServeUrl() {
        return config.getPublicServeUrl();
    }

    public Path getPublicDiskPath() {
        return publicDiskPath;
    }

    public String getEmbeddedServeUrl() {
        return config.getEmbeddedServeUrl();
    }

}
