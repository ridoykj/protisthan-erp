package com.itbd.protisthan.db.dao.iddao;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class OrderDetailId implements Serializable {
    private static final long serialVersionUID = -7240693046262475946L;
    @NotNull
    @Column(name = "id_order_key", nullable = false)
    private Long idOrderKey;

    @NotNull
    @Column(name = "id_item_key", nullable = false)
    private Long idItemKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderDetailId entity = (OrderDetailId) o;
        return Objects.equals(this.idItemKey, entity.idItemKey) &&
                Objects.equals(this.idOrderKey, entity.idOrderKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItemKey, idOrderKey);
    }

}