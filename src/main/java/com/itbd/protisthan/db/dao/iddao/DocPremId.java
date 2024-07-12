package com.itbd.protisthan.db.dao.iddao;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class DocPremId implements Serializable {
    private static final long serialVersionUID = -8121668247117169318L;

    @Size(max = 140)
    @NotNull
    @Column(name = "tx_role_key", nullable = false, length = 140)
    private String roleKey;

    @Size(max = 140)
    @NotNull
    @Column(name = "tx_doc_type_key", nullable = false)
    private String docTypeKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DocPremId entity = (DocPremId) o;
        return Objects.equals(this.docTypeKey, entity.docTypeKey) &&
                Objects.equals(this.roleKey, entity.roleKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docTypeKey, roleKey);
    }

}