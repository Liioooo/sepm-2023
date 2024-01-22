package at.ac.tuwien.sepr.groupphase.backend.integrationtest.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.datagenerator.ApplicationUserDataGenerator;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.EmbeddedFile;
import at.ac.tuwien.sepr.groupphase.backend.repository.EmbeddedFileRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test", "generateData"})
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = AFTER_CLASS)
class EmbeddedFilesEndpoint {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApplicationUserDataGenerator applicationUserDataGenerator;

    @Autowired
    private EmbeddedFileRepository embeddedFileRepository;

    private final String API_BASE = "/api/v1/files";

    private EmbeddedFile file;
    private ApplicationUser user;
    private ApplicationUser otherUser;

    @BeforeAll
    void setupData() {
        user = applicationUserDataGenerator.getTestData().get(2);
        otherUser = applicationUserDataGenerator.getTestData().get(3);
        setupFile();
    }

    private void setupFile() {
        file = embeddedFileRepository.save(
            EmbeddedFile.builder()
            .data("test".getBytes())
            .mimeType("text/plain")
            .allowedViewer(user)
            .build()
        );
    }

    @Test
    void getEmbeddedFile_byId_withNoUser_isForbidden() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get(API_BASE + "/" + file.getId())
                .accept(MediaType.ALL)
            ).andExpect(
                status().isForbidden()
            );
        });
    }

    @Test
    void getEmbeddedFile_byWrongId_withUser_returnsNotFound() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get(API_BASE + "/" + (file.getId() + 1))
                .with(user(user.getEmail())
                    .roles(user.getRole().toString().replace("ROLE_", "")))
                .accept(MediaType.ALL)
            ).andExpect(
                status().isNotFound()
            );
        });
    }

    @Test
    void getEmbeddedFile_byId_withIncorrectUser_returnsForbidden() {
        assertDoesNotThrow(() -> {
            this.mockMvc.perform(get(API_BASE + "/" + file.getId())
                .with(user(otherUser.getEmail())
                    .roles(otherUser.getRole().toString().replace("ROLE_", "")))
                .accept(MediaType.ALL)
            ).andExpect(
                status().isForbidden()
            );
        });
    }

    @Test
    void getEmbeddedFile_byId_withCorrentUser_returnsCorrectFileAndMimeType() {
        assertDoesNotThrow(() -> {
            String content = this.mockMvc.perform(get(API_BASE + "/" + file.getId())
                .accept(MediaType.ALL)
                .with(user(user.getEmail())
                    .roles(user.getRole().toString().replace("ROLE_", "")))
            ).andExpect(
                status().isOk()
            ).andExpect(
                content().contentType(MediaType.TEXT_PLAIN)
            ).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

            assertEquals("test", content);
        });
    }
}
