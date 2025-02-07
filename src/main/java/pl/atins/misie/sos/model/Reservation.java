package pl.atins.misie.sos.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="Reservation")
public class Reservation {
    @Id
    @Column(name="id")
    public Integer id;

    @Column(name="reservation_dare", nullable=false)
    private LocalDate reservationDare;

    @Column(name="reservation_status", nullable=false)
    private String reservationStatus;

    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne(optional=false)
    private User user;

    @JoinColumn(name="library_item_id", nullable=false)
    @ManyToOne(optional=false)
    private LibraryItem libraryItem;
}
