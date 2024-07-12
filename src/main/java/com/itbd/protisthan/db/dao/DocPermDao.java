package com.itbd.protisthan.db.dao;

import com.itbd.protisthan.db.dao.iddao.DocPremId;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "t_doc_perm", schema = "erp_over", uniqueConstraints = {
        @UniqueConstraint(name = "uk_doc_perm_role_doc_type", columnNames = {"tx_role_key", "tx_doc_type_key"})
})
public class DocPermDao {

    @EmbeddedId
    private DocPremId id;

    @MapsId("roleKey")
//    @MapsId
    @ManyToOne( optional = false)
    @JoinColumn(name = "tx_role_key", nullable = false)
    private RoleDao role;

    @MapsId("docTypeKey")
//    @MapsId
    @ManyToOne(optional = false)
    @JoinColumn(name = "tx_doc_type_key", nullable = false)
    private DocTypeDao docType;


    @Size(max = 255)
    @Column(name = "tx_name")
    private String name;

    @Column(name = "is_amend")
    private Boolean isAmend;

    @Column(name = "is_cancel")
    private Boolean isCancel;

    @Column(name = "is_create")
    private Boolean isCreate;

    @Column(name = "dtt_creation")
    private Instant creation;

    @Column(name = "is_delete")
    private Boolean isDelete;

    @Column(name = "is_email")
    private Boolean isEmail;

    @Column(name = "is_export")
    private Boolean isExport;

    @Column(name = "ct_idx")
    private Integer ctIdx;

    @Column(name = "is_if_owner")
    private Boolean isIfOwner;

    @Column(name = "is_import")
    private Boolean isImport;

    @Column(name = "is_doc_status")
    private Boolean isDocStatus;

    @Column(name = "dtt_modified")
    private Instant dttModified;

    @Size(max = 140)
    @Column(name = "tx_modified_by", length = 140)
    private String modifiedBy;

    @Size(max = 255)
    @Column(name = "tx_owner")
    private String owner;


    @Column(name = "ct_perm_level")
    private Integer permLevel;

    @Column(name = "is_print")
    private Boolean isPrint;

    @Column(name = "is_read")
    private Boolean isRead;

    @Column(name = "is_report")
    private Boolean isReport;

    @Column(name = "is_select")
    private Boolean isSelect;

    @Column(name = "is_share")
    private Boolean isShare;

    @Column(name = "is_submit")
    private Boolean isSubmit;

    @Column(name = "is_write")
    private Boolean isWrite;


}