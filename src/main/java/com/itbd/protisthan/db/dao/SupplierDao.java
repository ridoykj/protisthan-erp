package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "suppliers", schema = "erp_over")
public class SupplierDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_supplier_key", nullable = false)
    private Long id;

    @Size(max = 40)
//    @NotNull
    @Column(name = "tx_company_name", nullable = false, length = 40)
    private String companyName;

    @Size(max = 30)
    @Column(name = "tx_contact_name", length = 30)
    private String contactName;

    @Size(max = 30)
    @Column(name = "tx_contact_title", length = 30)
    private String contactTitle;

    @Size(max = 60)
    @Column(name = "tx_address", length = 60)
    private String address;

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
    @Column(name = "tx_email", length = 24)
    private String email;

    @Size(max = 24)
    @Column(name = "tx_fax", length = 24)
    private String fax;

    @Lob
    @Column(name = "tx_homepage")
    private String homepage;

//    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_supplier_group_key", nullable = false)
    private SupplierGroupDao supplierGroup;

    @ManyToOne
    @JoinColumn(name = "id_country_key")
    private CountryDao country;


}