package pl.atins.misie.sos.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Subject")
public class Subject {
    @Id
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

    @JoinColumn(name="lecturer_id", nullable=false)
    @ManyToOne(optional=true)
    private User lecturer;

    @JoinColumn(name="department_id", nullable=false)
    @ManyToOne(optional=false)
    private Department department;
}
