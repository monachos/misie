package pl.atins.misie.sos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Library_Item")
@Getter
@Setter
public class LibraryItem {
    @Id
    @Column(name="id")
    public Integer id;

    @Column(name="quantity", nullable=false)
    private Integer quantity;
}
