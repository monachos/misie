package pl.atins.misie.sos.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="Grade")
public class Grade {
    @Id
    @Column(name="id")
    public Integer id;

    @JoinColumn(name="student_subject_id", nullable=false)
    @ManyToOne(optional=false)
    private StudentSubject studentSubject;

    @Column(name="grade", nullable=false)
    private BigDecimal grade;

    @Column(name="type", nullable=false)
    private String type;

    @Column(name="grading_date", nullable=false)
    private LocalDateTime gradingDate;
}
