package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="SOS_Role")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="department_id", nullable=false)
    private Department department;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="is_admin", nullable=false)
    private Boolean isAdmin;
}
