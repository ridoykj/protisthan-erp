package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_mode_of_payment", schema = "erp_over")
public class ModeOfPaymentDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mode_of_payment_key", nullable = false)
    private Long id;

    @Size(max = 140)
    @Column(name = "tx_name", length = 140)
    private String name;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "ct_idx")
    private Integer idx;

//    @Size(max = 140)
//    @Column(name = "tx_mode_of_payment", length = 140)
//    private String modeOfPayment;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Size(max = 140)
    @Column(name = "tx_owner", length = 140)
    private String owner;

    @Size(max = 140)
    @Column(name = "tx_type", length = 140)
    private String type;

}