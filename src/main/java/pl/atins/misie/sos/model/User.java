package pl.atins.misie.sos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="SOS_User")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="student_id_number", unique=true)
    private String studentIdNumber;

    @Column(name="active", nullable=false)
    private Boolean active;

    @Column(name="deleted", nullable=false)
    private Boolean deleted;

    @Column(name="email", unique=true, nullable=false)
    private String email;

    @Column(name="password", nullable=false)
    private String password;

    @Column(name="phone_2fa")
    private String phone2fa;

    @Column(name="accepted_privacy_policy", nullable=false)
    private Boolean acceptedPrivacyPolicy;

    @Column(name="accepted_terms_of_use", nullable=false)
    private Boolean acceptedTermsOfUse;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="surname", nullable=false)
    private String surname;

    @OneToOne(optional=false)
    @JoinColumn(name="registered_address", nullable=false)
    private Address registeredAddress;

    @OneToOne(optional=true)
    @JoinColumn(name="residential_address")
    private Address residentialAddress;

    @OneToOne(optional=true)
    @JoinColumn(name="correspondence_address")
    private Address correspondenceAddress;

    @Column(name="blocked_account", nullable=false)
    private Boolean blockedAccount;

    @Column(name="block_time")
    private LocalDateTime blockTime;
}
