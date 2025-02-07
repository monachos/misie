package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="Payment")
@Getter
@Setter
public class Payment {
    @Id
    @Column(name="id")
    public Integer id;

    @JoinColumn(name="student_id", nullable=false)
    @ManyToOne(optional=false)
    private User student;

    @Column(name="type", nullable=false)
    private String type;

    @Column(name="amount", nullable=false)
    private BigDecimal amount;
}
