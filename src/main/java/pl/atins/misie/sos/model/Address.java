package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="address_line_1", nullable=false)
    private String addressLine1;

    @Column(name="address_line_2")
    private String addressLine2;


}
