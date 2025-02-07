package pl.atins.misie.sos.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="University_Class")
public class UniversityClass {
    @Id
    @Column(name="id")
    public Integer id;

    @JoinColumn(name="subject_id", nullable=false)
    @ManyToOne(optional=false)
    private Subject subject;

    @Column(name="start_date", nullable=false)
    private LocalDateTime startDate;

    @Column(name="end_date", nullable=false)
    private LocalDateTime endDate;
}
