package pl.atins.misie.sos.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Student_Subject")
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
