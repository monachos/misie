package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="University_Class")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversityClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    public Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="subject_id", nullable=false)
    private Subject subject;

    @Column(name="start_date", nullable=false)
    private LocalDateTime startDate;

    @Column(name="end_date", nullable=false)
    private LocalDateTime endDate;
}
