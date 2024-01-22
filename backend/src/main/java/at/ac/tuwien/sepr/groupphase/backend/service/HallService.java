package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Hall;
import at.ac.tuwien.sepr.groupphase.backend.exception.ConflictException;

public interface HallService {
    /**
     * Creates a new hall.
     *
     * @param hallCreateDto data of the hall to create
     *
     * @throws ConflictException If the location of the DTO does not exist
     */
    Hall createHall(HallCreateDto hallCreateDto);
}