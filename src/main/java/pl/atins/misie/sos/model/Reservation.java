package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Reservation")
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Integer id;

    @Column(name="reservation_date", nullable=false)
    private LocalDateTime reservationDate;

    @Column(name="reservation_status", nullable=false)
    private String reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="library_item_id", nullable=false)
    private LibraryItem libraryItem;
}
