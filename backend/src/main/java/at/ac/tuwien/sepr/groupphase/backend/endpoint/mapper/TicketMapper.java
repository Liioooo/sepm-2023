package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.CreateTicketDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import org.mapstruct.Mapper;

@Mapper
public interface TicketMapper {

    Ticket createTicketDtoToTicket(CreateTicketDto createTicketDto);

}
