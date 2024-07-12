package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "t_user", schema = "erp_over")
public class UserDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_key", nullable = false)
    private Long id;

    @Size(max = 140)
    @Column(name = "tx_name", length = 140)
    private String name;

    @Lob
    @Column(name = "tx_bio")
    private String bio;

    @Column(name = "dt_birth_date")
    private LocalDate birthDate;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Size(max = 140)
    @Column(name = "tx_email", length = 140)
    private String email;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Size(max = 140)
    @Column(name = "tx_full_name", length = 140)
    private String fullName;

    @Column(name = "ct_idx")
    private Integer ctIdx;

    @Size(max = 140)
    @Column(name = "tx_last_login", length = 140)
    private String lastLogin;

    @Size(max = 140)
    @Column(name = "tx_location", length = 140)
    private String location;

    @Lob
    @Column(name = "tx_password")
    private String password;

    @Size(max = 140)
    @Column(name = "tx_phone", length = 140)
    private String phone;

    @Lob
    @Column(name = "tx_user_image")
    private String userImage;

    @Size(max = 140)
    @Column(name = "tx_username", length = 140)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tx_user_type_key", nullable = false)
    private UserTypeDao userType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_employee_key")
    private EmployeeDao employee;

//    @ManyToMany
//    private Set<RoleDao> roles;
}