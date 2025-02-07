package pl.atins.misie.sos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Address")
@Getter
@Setter
public class Address {
    @Id
    @Column(name="id")
    public Integer id;

    @Column(name="address_line_1", nullable=false)
    public String addressLine1;

    @Column(name="address_line_2", nullable=false)
    public String addressLine2;
}
