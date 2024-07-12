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
@Table(name = "t_language", schema = "erp_over")
public class LanguageDao {
    @Id
    @Size(max = 16)
    @Column(name = "id_language_key", nullable = false, length = 16)
    private String id;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Size(max = 140)
    @Column(name = "tx_flag", length = 140)
    private String flag;

    @Column(name = "ct_idx")
    private Integer idx;

    @Size(max = 140)
    @Column(name = "tx_language_code", length = 140)
    private String code;

    @Size(max = 140)
    @Column(name = "tx_language_name", length = 140)
    private String name;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

}