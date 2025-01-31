package at.ac.tuwien.sepr.groupphase.backend.repository;

import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import at.ac.tuwien.sepr.groupphase.backend.entity.interfaces.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.order.event.id = :eventId AND t.seatNumber = :seatNumber AND t.rowNumber = :rowNumber AND t.ticketCategory = at.ac.tuwien.sepr.groupphase.backend.enums.TicketCategory.SEATING "
        + "AND (t.order.orderType = at.ac.tuwien.sepr.groupphase.backend.enums.OrderType.BUY "
        + "  OR (t.order.orderType = at.ac.tuwien.sepr.groupphase.backend.enums.OrderType.RESERVE "
        + "    AND DATEDIFF(MINUTE, CURRENT_TIMESTAMP, t.order.event.startDate) > 30))")
    List<Ticket> findValidSeatingTicketsByEventIdAndSeatNumberAndRowNumber(@Param("eventId") Long eventId, @Param("seatNumber") Long seatNumber, @Param("rowNumber") Long rowNumber);

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.order.event.id = :eventId AND t.ticketCategory = at.ac.tuwien.sepr.groupphase.backend.enums.TicketCategory.STANDING "
        + "AND (t.order.orderType = at.ac.tuwien.sepr.groupphase.backend.enums.OrderType.BUY "
        + "  OR (t.order.orderType = at.ac.tuwien.sepr.groupphase.backend.enums.OrderType.RESERVE "
        + "    AND DATEDIFF(MINUTE, CURRENT_TIMESTAMP, t.order.event.startDate) > 30))")
    int findValidStandingTicketsByEventId(@Param("eventId") Long eventId);

    @Query(
        "SELECT t.rowNumber AS rowNumber, t.seatNumber AS seatNumber FROM Ticket t WHERE t.order.event.id = :eventId AND t.ticketCategory = at.ac.tuwien.sepr.groupphase.backend.enums.TicketCategory.SEATING "
            + "AND (t.order.orderType = at.ac.tuwien.sepr.groupphase.backend.enums.OrderType.BUY "
            + "  OR (t.order.orderType = at.ac.tuwien.sepr.groupphase.backend.enums.OrderType.RESERVE "
            + "    AND DATEDIFF(MINUTE, CURRENT_TIMESTAMP, t.order.event.startDate) > 30))")
    List<Seat> findOccupiedSeatsById(@Param("eventId") long id);

    Optional<Ticket> findByUuid(UUID uuid);
}
