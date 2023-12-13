package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.ApiErrorDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.EmbeddedFile;
import at.ac.tuwien.sepr.groupphase.backend.exception.NotFoundException;
import at.ac.tuwien.sepr.groupphase.backend.repository.EmbeddedFileRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/files")
public class EmbeddedFileEndpoint {

    private final UserService userService;

    private final EmbeddedFileRepository embeddedFileRepository;

    private final ObjectMapper objectMapper;

    public EmbeddedFileEndpoint(UserService userService, EmbeddedFileRepository embeddedFileRepository, ObjectMapper objectMapper) {
        this.userService = userService;
        this.embeddedFileRepository = embeddedFileRepository;
        this.objectMapper = objectMapper;
    }

    @Secured("ROLE_USER")
    @GetMapping(path = "/{id}")
    @Operation(summary = "Get embedded file with specified id if it belongs to the currently authenticated user")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable long id) throws JsonProcessingException {
        EmbeddedFile file = embeddedFileRepository.findById(id).orElseThrow(NotFoundException::new);
        ApplicationUser user = userService.getCurrentlyAuthenticatedUser().orElseThrow(() -> new NotFoundException("No user currently logged in"));

        if (!Objects.equals(file.getAllowedViewer().getId(), user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ByteArrayResource(
                    objectMapper.writeValueAsBytes(new ApiErrorDto(HttpStatus.FORBIDDEN, "You do not have access this resource."))
                ));
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(file.getMimeType()))
            .body(new ByteArrayResource(file.getData()));
    }
}
