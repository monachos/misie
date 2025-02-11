package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="Student_Subject")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="student_id", nullable=false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="subject_id", nullable=false)
    private Subject subject;

    @Column(name="registration_time")
    private LocalDateTime registrationTime;
}
