package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="Grade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="student_subject_id", nullable=false)
    private StudentSubject studentSubject;

    @Column(name="grade", nullable=false, precision=2, scale=1)
    private BigDecimal grade;

    @Column(name="type", nullable=false)
    private String type;

    @Column(name="grading_date", nullable=false)
    private LocalDateTime gradingDate;
}
