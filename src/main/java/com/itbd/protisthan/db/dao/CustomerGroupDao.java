package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_customer_group", schema = "erp_over")
public class CustomerGroupDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer_group_key", nullable = false)
    private Long id;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Size(max = 140)
    @Column(name = "tx_owner", length = 140)
    private String owner;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Size(max = 140)
    @Column(name = "tx_customer_group_name", length = 140)
    private String name;

    @Size(max = 140)
    @Column(name = "tx_default_price_list", length = 140)
    private String defaultPriceList;

    @Column(name = "is_group")
    private Boolean isGroup;

    @Size(max = 140)
    @Column(name = "tx_payment_terms", length = 140)
    private String paymentTerms;

    @Column(name = "ct_idx")
    private Integer idx;

}