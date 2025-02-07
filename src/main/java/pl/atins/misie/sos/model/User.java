package pl.atins.misie.sos.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="SOS_User")
public class User {
    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="student_id_number", unique=true)
    private String studentIdNumber;

    @Column(name="active", unique=true)
    private Boolean active;

    @Column(name="deleted", unique=true)
    private Boolean deleted;

    @Column(name="email", unique=true)
    private String email;

    @Column(name="password", unique=true)
    private String password;

    @Column(name="phone_2fa")
    private String phone2fa;

    @Column(name="accepted_privacy_policy", nullable=false)
    private Boolean acceptedPrivacyPolicy;

    @Column(name="accepted_terms_of_use", nullable=false)
    private Boolean acceptedTermsOfUse;

    @Column(name="name", unique=true)
    private String name;

    @Column(name="surname", unique=true)
    private String surname;

    @JoinColumn(name="registered_address", nullable=false)
    @OneToOne(optional=false)
    private Address registeredAddress;

    @JoinColumn(name="residential_address")
    @OneToOne(optional=true)
    private Address residentialAddress;

    @JoinColumn(name="correspondence_address", nullable=true)
    @OneToOne(optional=true)
    private Address correspondenceAddress;

    @Column(name="blocked_account", nullable=false)
    private Boolean blockedAccount;

    @Column(name="block_time")
    private LocalDateTime blockTime;
}
