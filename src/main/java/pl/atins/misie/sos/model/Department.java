package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name", nullable=false)
    private String name;
}
