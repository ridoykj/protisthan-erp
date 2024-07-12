package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_country", schema = "erp_over")
public class CountryDao {
    @Id
    @Size(max = 16)
    @Column(name = "id_country_key", nullable = false, length = 16)
    private String id;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Size(max = 140)
    @Column(name = "tx_owner", length = 140)
    private String owner;

    @Column(name = "tx_code", nullable = false, length = 16)
    private String code;

    @Size(max = 140)
    @Column(name = "tx_country_name", length = 140)
    private String name;

    @Size(max = 140)
    @Column(name = "tx_date_format", length = 140)
    private String dateFormat;

    @Size(max = 140)
    @Column(name = "tx_time_format", length = 140)
    private String timeFormat;

    @Lob
    @Column(name = "tx_time_zones", columnDefinition = "TEXT")
//    @JdbcTypeCode(Types.LONGNVARCHAR)
    private String timeZones;

    @Column(name = "ct_idx")
    private Integer idx;
}