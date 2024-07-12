package com.itbd.protisthan.db.dao;

import com.itbd.protisthan.constant.enums.NameSeriesEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_name_series", schema = "erp_over")
public class NameSeriesDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_name_series_key", nullable = false)
    private Long id;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Size(max = 16)
    @Column(name = "tx_short", length = 16)
    private String shortName;

    @Size(max = 140)
    @Column(name = "tx_name", length = 140)
    private String name;

    @Lob
    @Column(name = "tx_description")
    private String description;

    @Column(name = "tx_type")
    @Enumerated(EnumType.STRING)
    private NameSeriesEnum type;

    @Column(name = "ct_idx")
    private Integer idx;
}