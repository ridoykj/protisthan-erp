package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_department", schema = "erp_over", indexes = {
        @Index(name = "id_parent_department_key", columnList = "id_parent_department_key")
})
public class DepartmentDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_department_key", nullable = false)
    private Long id;

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


    @Size(max = 140)
    @Column(name = "tx_name", length = 140)
    private String name;

    @Size(max = 140)
    @Column(name = "tx_company", length = 140)
    private String company;

    @Column(name = "is_disabled")
    private Boolean isDisabled;

    @Column(name = "is_group")
    private Boolean isGroup;

    @Column(name = "ct_idx")
    private Integer idx;

    @ManyToOne
    @JoinColumn(name = "id_parent_department_key", referencedColumnName = "id_department_key")
    private DepartmentDao parentDepartment;
}