package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="Lending")
@Getter
@Setter
public class Lending {
    @Id
    @Column(name="id")
    public Integer id;

    @Column(name="lending_date", nullable=false)
    private LocalDateTime lendingDate;

    @Column(name="return_date")
    private String returnDate;

    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne(optional=false)
    private User user;

    @JoinColumn(name="library_item_id", nullable=false)
    @ManyToOne(optional=false)
    private LibraryItem libraryItem;
}
