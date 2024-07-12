package com.itbd.protisthan.db.dao;

import com.itbd.protisthan.constant.enums.BloodGroupEnum;
import com.itbd.protisthan.constant.enums.ReligionEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employees", schema = "erp_over", indexes = {
        @Index(name = "id_reports_to", columnList = "id_reports_to")
}, uniqueConstraints = {
        @UniqueConstraint(name = "tx_employee_id", columnNames = {"tx_employee_id"})
})
public class EmployeeDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee_key", nullable = false)
    private Long id;

    @Size(max = 20)
    @NotNull
    @Column(name = "tx_employee_id", nullable = false, length = 20)
    private String employeeId;

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

    @Size(max = 30)
    @Column(name = "tx_father_name", length = 30)
    private String fatherName;

    @Size(max = 30)
    @Column(name = "tx_mother_name", length = 30)
    private String motherName;

    @Column(name = "dt_birth_date")
    private LocalDate birthDate;


    @Column(name = "tx_blood_group")
    @Enumerated(EnumType.STRING)
    private BloodGroupEnum bloodGroup;


    @Column(name = "tx_religion")
    @Enumerated(EnumType.STRING)
    private ReligionEnum religion;

    @Size(max = 30)
    @Column(name = "tx_nationality", length = 30)
    private String nationality;

    @Size(max = 30)
    @Column(name = "tx_nid", length = 30)
    private String nid;

    @Size(max = 64)
    @Column(name = "tx_passport_number", length = 64)
    private String passportNumber;

    @Size(max = 16)
    @Column(name = "tx_marital_status", length = 16)
    private String maritalStatus;

    @Size(max = 30)
    @Column(name = "tx_spouse_name", length = 30)
    private String spouseName;

    @Column(name = "ct_children")
    private Integer children;

    @Size(max = 127)
    @Column(name = "tx_permanent_address", length = 127)
    private String permanentAddress;

    @Size(max = 127)
    @Column(name = "tx_present_address", length = 127)
    private String presentAddress;

    @Size(max = 25)
    @Column(name = "tx_title_of_courtesy", length = 25)
    private String titleOfCourtesy;

    @Column(name = "dt_hire_date")
    private LocalDate hireDate;

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

    @Size(max = 4)
    @Column(name = "tx_extension", length = 4)
    private String extension;

    @Lob
    @Column(name = "tx_photo")
    private String photo;

    @Lob
    @Column(name = "tx_notes")
    private String notes;

    @Size(max = 255)
    @Column(name = "tx_photo_path")
    private String photoPath;

    @Size(max = 32)
    @Column(name = "tx_bank_ac_no", length = 32)
    private String bankAcNo;

    @Size(max = 100)
    @Column(name = "tx_bank_name", length = 100)
    private String bankName;

    @Column(name = "dt_date_of_retirement")
    private LocalDate dateOfRetirement;

    @Column(name = "dt_held_on")
    private LocalDate heldOn;

    @Size(max = 64)
    @Column(name = "tx_iban", length = 64)
    private String iban;

    @Size(max = 256)
    @Column(name = "tx_image", length = 256)
    private String image;

    @Size(max = 256)
    @Column(name = "tx_reason_for_leaving", length = 256)
    private String reasonForLeaving;

    @Size(max = 4)
    @Column(name = "tx_salary_currency", length = 4)
    private String salaryCurrency;

    @Size(max = 32)
    @Column(name = "tx_salary_mode", length = 32)
    private String salaryMode;

    @Column(name = "flt_salary")
    private BigDecimal salary;

    @Column(name = "is_disabled")
    private Boolean isDisabled;

    @Lob
    @Column(name = "tx_bio")
    private String bio;

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
    @JoinColumn(name = "id_designation_key")
    private DesignationDao designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_name_series_key")
    private NameSeriesDao namingSeries;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_department_key")
    private DepartmentDao department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reports_to", referencedColumnName = "id_employee_key")
    private EmployeeDao reportsTo;

}