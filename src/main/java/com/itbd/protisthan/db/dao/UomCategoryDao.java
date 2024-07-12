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
@Table(name = "t_uom_category", schema = "erp_over")
public class UomCategoryDao {
    @Id
    @Size(max = 140)
    @Column(name = "tx_uom_category_key", nullable = false, length = 140)
    private String id;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "ct_idx")
    private Integer idx;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

}