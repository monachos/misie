package pl.atins.misie.sos.model;

import jakarta.persistence.*;

@Entity
@Table(name="SOS_Role")
public class Role   {
    @Id
    @Column(name="id")
    public Integer id;

    @JoinColumn(name="department_id", nullable=false)
    @ManyToOne(optional=false)
    private Department department;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="is_admin", nullable=false)
    private Boolean isAdmin;
}
