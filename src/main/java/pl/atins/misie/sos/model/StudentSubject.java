package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="Student_Subject")
@Getter
@Setter
public class StudentSubject {
    @Id
    @Column(name="id")
    public Integer id;

    @JoinColumn(name="student_id", nullable=false)
    @ManyToOne(optional=false)
    private User student;

    @JoinColumn(name="subject_id", nullable=false)
    @ManyToOne(optional=false)
    private Subject subject;

    @Column(name="registration_time")
    private LocalDateTime registrationTime;
}
