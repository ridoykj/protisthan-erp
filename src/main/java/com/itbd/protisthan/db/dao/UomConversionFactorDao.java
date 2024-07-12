package com.itbd.protisthan.db.dao;

import com.itbd.protisthan.db.dao.iddao.UomConversionId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_uom_conversion_factor", schema = "erp_over")
public class UomConversionFactorDao {

    @EmbeddedId
    private UomConversionId id;

    @MapsId("fromUomKey")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_from_uom_key", nullable = false)
    private UomDao fromUomKey;

    @MapsId("toUomKey")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_to_uom_key", nullable = false)
    private UomDao toUomKey;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "ct_idx")
    private Integer idx;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Size(max = 140)
    @Column(name = "tx_owner", length = 140)
    private String owner;

    @Column(name = "flt_value", precision = 21, scale = 9)
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tx_uom_category_key", nullable = false)
    private UomCategoryDao category;
}