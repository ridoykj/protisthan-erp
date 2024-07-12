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
@Table(name = "t_user_type", schema = "erp_over")
public class UserTypeDao {
    @Id
    @Size(max = 140)
    @Column(name = "tx_user_type_key", nullable = false, length = 140)
    private String id;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "ct_idx")
    private Integer idx;

    @Column(name = "is_standard")
    private Boolean isStandard;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Size(max = 140)
    @Column(name = "tx_owner", length = 140)
    private String owner;

    @Size(max = 140)
    @Column(name = "tx_role", length = 140)
    private String role;

}