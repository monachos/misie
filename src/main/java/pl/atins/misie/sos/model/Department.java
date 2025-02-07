package pl.atins.misie.sos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Department")
public class Department {
    @Id
    @Column(name="id")
    public Integer id;

    @Column(name="name", nullable=false)
    private String name;
}
