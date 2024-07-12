package com.itbd.protisthan.db.dao;

import com.itbd.protisthan.constant.enums.BloodGroupEnum;
import com.itbd.protisthan.constant.enums.ReligionEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "customers", schema = "erp_over", indexes = {
        @Index(name = "id_gender_key", columnList = "id_gender_key")
}, uniqueConstraints = {
        @UniqueConstraint(name = "tx_customer_id", columnNames = {"tx_customer_id"})
})
public class CustomerDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer_key", nullable = false)
    private Long id;

    @Size(max = 20)
    @NotNull
    @Column(name = "tx_customer_id", nullable = false, length = 20)
    private String customerId;

    @Size(max = 64)
    @NotNull
    @Column(name = "tx_first_name", nullable = false, length = 64)
    private String firstName;

    @Size(max = 64)
    @NotNull
    @Column(name = "tx_last_name", nullable = false, length = 64)
    private String lastName;


    @Size(max = 30)
    @Column(name = "tx_title", length = 30)
    private String title;


    @Column(name = "dt_birth_date")
    private LocalDate birthDate;

    @Size(max = 64)
    @Column(name = "tx_company_name", length = 64)
    private String companyName;

    @Column(name = "tx_blood_group")
    @Enumerated(EnumType.STRING)
    private BloodGroupEnum bloodGroup;

    @Column(name = "tx_religion")
    @Enumerated(EnumType.STRING)
    private ReligionEnum religion;

    @Size(max = 124)
    @Column(name = "tx_home_address", length = 124)
    private String homeAddress;

    @Size(max = 124)
    @Column(name = "tx_office_address", length = 124)
    private String officeAddress;

    @Size(max = 15)
    @Column(name = "tx_city", length = 15)
    private String city;

    @Size(max = 15)
    @Column(name = "tx_region", length = 15)
    private String region;

    @Size(max = 10)
    @Column(name = "tx_postal_code", length = 10)
    private String postalCode;

    @Size(max = 24)
    @Column(name = "tx_phone", length = 24)
    private String phone;

    @Size(max = 24)
    @Column(name = "tx_telephone", length = 24)
    private String telephone;

    @Size(max = 100)
    @Column(name = "tx_email", length = 100)
    private String email;

    @Size(max = 24)
    @Column(name = "tx_fax", length = 24)
    private String fax;

    @Size(max = 256)
    @Column(name = "tx_customer_details", length = 256)
    private String customerDetails;

    @ColumnDefault("b'0'")
    @Column(name = "is_disabled", nullable = false)
    private Boolean isDisabled = false;

    @Size(max = 256)
    @Column(name = "tx_image", length = 256)
    private String image;

    @Size(max = 16)
    @Column(name = "tx_marital_status", length = 16)
    private String maritalStatus;

    @Size(max = 30)
    @Column(name = "tx_spouse_name", length = 30)
    private String spouseName;

    @Column(name = "ct_children")
    private Integer children;


    @Size(max = 128)
    @Column(name = "tx_tax_id", length = 128)
    private String taxId;

    @Size(max = 30)
    @Column(name = "tx_nid", length = 30)
    private String nid;

    @Size(max = 140)
    @Column(name = "tx_territory", length = 140)
    private String territory;

    @Lob
    @Column(name = "tx_notes")
    private String notes;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gender_key")
    private GenderDao gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_language_key")
    private LanguageDao language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country_key")
    private CountryDao country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_name_series_key")
    private NameSeriesDao namingSeries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer_group_key")
    private CustomerGroupDao group;

}