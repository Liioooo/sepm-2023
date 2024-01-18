package at.ac.tuwien.sepr.groupphase.backend.endpoint.mapper;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TicketCreateDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.TicketListDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import org.mapstruct.Mapper;

@Mapper
public interface TicketMapper {

    TicketListDto toTicketListDto(Ticket ticketListDto);

    Ticket createTicketDtoToTicket(TicketCreateDto ticketCreateDto);

}
