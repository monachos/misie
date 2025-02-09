package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Library_Item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Integer id;

    @Column(name="quantity", nullable=false)
    private Integer quantity;
}
