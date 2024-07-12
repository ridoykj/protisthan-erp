package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@ToString
@Entity
@Table(name = "t_gender", schema = "erp_over", indexes = {@Index(name = "tx_name", columnList = "tx_name")
}, uniqueConstraints = {
        @UniqueConstraint(name = "tx_name", columnNames = "tx_name")
})
public class GenderDao {
    @Id
    @Column(name = "id_gender_key", nullable = false)
    private Long id;

    @Size(max = 140)
    @Column(name = "tx_name", length = 140)
    private String name;

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