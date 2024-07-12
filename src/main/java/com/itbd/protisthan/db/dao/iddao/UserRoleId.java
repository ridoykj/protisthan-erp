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
public class UserRoleId implements Serializable {
    private static final long serialVersionUID = -8121668247117169378L;

    @NotNull
    @Column(name = "id_user_key", nullable = false)
    private Long userKey;

    @Size(max = 140)
    @NotNull
    @Column(name = "tx_role_key", nullable = false)
    private String roleKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserRoleId entity = (UserRoleId) o;
        return Objects.equals(this.roleKey, entity.roleKey) &&
                Objects.equals(this.userKey, entity.userKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleKey, userKey);
    }

}