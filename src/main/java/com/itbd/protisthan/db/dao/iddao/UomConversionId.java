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
public class UomConversionId implements Serializable {
    private static final long serialVersionUID = -8121668247117169378L;

    @Size(max = 140)
    @NotNull
    @Column(name = "id_from_uom_key", nullable = false, length = 140)
    private String fromUomKey;

    @Size(max = 140)
    @NotNull
    @Column(name = "id_to_uom_key", nullable = false)
    private String toUomKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UomConversionId entity = (UomConversionId) o;
        return Objects.equals(this.toUomKey, entity.toUomKey) &&
                Objects.equals(this.fromUomKey, entity.fromUomKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toUomKey, fromUomKey);
    }

}