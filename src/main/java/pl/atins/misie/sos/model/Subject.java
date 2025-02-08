package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="Subject")
@Getter
@Setter
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Integer id;

    @Column(name="time", nullable=false)
    private LocalDateTime time;

    @Column(name="description", nullable=false)
    private String description;

    @Column(name="title", nullable=false)
    private String title;

    @Column(name="max_students", nullable=false)
    private Integer maxStudents;

    @Column(name="is_active")
    private Boolean isActive;

    @Column(name="semester", nullable=false)
    private String semester;

    @Column(name="academic_year", nullable=false)
    private Integer academicYear;

    @Column(name="registration_start", nullable=false)
    private LocalDateTime registrationStart;

    @Column(name="registration_end", nullable=false)
    private LocalDateTime registrationEnd;

    @Column(name="room_number", nullable=false)
    private Integer roomNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional=true)
    @JoinColumn(name="lecturer_id")
    private User lecturer;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="department_id", nullable=false)
    private Department department;
}
