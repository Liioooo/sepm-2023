package at.ac.tuwien.sepr.groupphase.backend.service;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.HallCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Hall;

public interface HallService {
    /**
     * Creates a new hall.
     *
     * @param hallCreateDto data of the hall to create
     */
    Hall createHall(HallCreateDto hallCreateDto);
}