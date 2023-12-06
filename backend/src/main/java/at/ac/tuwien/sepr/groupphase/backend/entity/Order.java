package at.ac.tuwien.sepr.groupphase.backend.entity;

import at.ac.tuwien.sepr.groupphase.backend.enums.OrderType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "`ORDER`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private OffsetDateTime orderDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(nullable = true)
    private OffsetDateTime cancellationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<Ticket> tickets;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ApplicationUser user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private EmbeddedFile pdfTickets;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private EmbeddedFile receipt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private EmbeddedFile cancellationReceipt;

}
