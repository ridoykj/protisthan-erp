package com.itbd.protisthan.db.dao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_uom", schema = "erp_over")
public class UomDao {
    @Id
    @Size(max = 140)
    @Column(name = "tx_uom_key", nullable = false, length = 140)
    private String id;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "ct_idx")
    private Integer idx;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Column(name = "is_must_be_whole_number")
    private Boolean isMustBeWholeNumber;

    @Size(max = 140)
    @Column(name = "tx_owner", length = 140)
    private String owner;

}