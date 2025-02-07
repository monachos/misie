package pl.atins.misie.sos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Address")
public class Address {
    @Id
    @Column(name="id")
    public Integer id;

    @Column(name="address_line_1", nullable=false)
    public String addressLine1;

    @Column(name="address_line_2", nullable=false)
    public String addressLine2;
}
