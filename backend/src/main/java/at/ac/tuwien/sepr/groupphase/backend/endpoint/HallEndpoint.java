package at.ac.tuwien.sepr.groupphase.backend.endpoint;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallDetailDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper.HallMapper;
import at.ac.tuwien.sepr.groupphase.backend.service.HallService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/halls")
public class HallEndpoint {
    private final HallService hallService;
    private final HallMapper hallMapper;

    public HallEndpoint(HallService hallService, HallMapper hallMapper) {
        this.hallService = hallService;
        this.hallMapper = hallMapper;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new Hall")
    public HallDetailDto createHall(@RequestBody @Valid HallCreateDto hallCreateDto) {
        return hallMapper.hallToHallDetailDto(hallService.createHall(hallCreateDto));
    }
}
