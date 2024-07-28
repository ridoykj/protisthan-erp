package com.itbd.protisthan.db.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_doc_type", schema = "erp_over", indexes = {
        @Index(name = "tx_parent_doc_type_key", columnList = "tx_parent_doc_type_key")
})
public class DocTypeDao {

    @Id
    @Size(max = 255)
    @Column(name = "tx_doc_type_key", nullable = false)
    private String id;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "is_custom")
    private Boolean isCustom;

    @Lob
    @Column(name = "tx_description")
    private String description;

    @Size(max = 140)
    @Column(name = "tx_documentation", length = 140)
    private String documentation;

    @Column(name = "dtt_modified")
    private Instant modified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Size(max = 140)
    @Column(name = "tx_module", length = 140)
    private String module;

    @Size(max = 40)
    @Column(name = "tx_naming_rule", length = 40)
    private String namingRule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tx_parent_doc_type_key", referencedColumnName = "tx_doc_type_key")
    private DocTypeDao docType;
}