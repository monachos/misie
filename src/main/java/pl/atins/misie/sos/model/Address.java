package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Address")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Integer id;

    @Column(name="address_line_1", nullable=false)
    public String addressLine1;

    @Column(name="address_line_2")
    public String addressLine2;
}
