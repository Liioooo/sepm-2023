package at.ac.tuwien.sepr.groupphase.backend.repository;

import at.ac.tuwien.sepr.groupphase.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.order.event.id = :eventId AND t.seatNumber = :seatNumber AND t.tierNumber = :tierNumber "
        + "AND t.order.cancellationDate IS NULL "
        + "AND (t.order.orderType = at.ac.tuwien.sepr.groupphase.backend.enums.OrderType.BUY "
        + "  OR (t.order.orderType = at.ac.tuwien.sepr.groupphase.backend.enums.OrderType.RESERVE "
        + "    AND DATEDIFF(MINUTE, t.order.orderDate, t.order.event.startDate) > 30))")
    public List<Ticket> findValidTicketsByEventIdAndSeatNumberAndTierNumber(
        @Param("eventId") Long eventId, @Param("seatNumber") Long seatNumber, @Param("tierNumber") Long tierNumber);
}
