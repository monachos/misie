package pl.atins.misie.sos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Department")
@Getter
@Setter
public class Department {
    @Id
    @Column(name="id")
    public Integer id;

    @Column(name="name", nullable=false)
    private String name;
}
