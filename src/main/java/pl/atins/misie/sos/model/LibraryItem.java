package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Library_Item")
@Getter
@Setter
public class LibraryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Integer id;

    @Column(name="quantity", nullable=false)
    private Integer quantity;
}
