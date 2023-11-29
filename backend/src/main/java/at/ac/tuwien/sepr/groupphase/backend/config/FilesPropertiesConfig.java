package at.ac.tuwien.sepr.groupphase.backend.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilesPropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "files")
    protected FilesConfig filesConfig() {
        return new FilesConfig();
    }

    @Getter
    @Setter
    public static class FilesConfig {
        private String publicServeUrl;
        private String publicDiskPath;
        private String embeddedServeUrl;

    }
}
