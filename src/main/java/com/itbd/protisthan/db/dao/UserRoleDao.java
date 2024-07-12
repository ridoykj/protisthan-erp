package com.itbd.protisthan.db.dao;

import com.itbd.protisthan.db.dao.iddao.UserRoleId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "t_user_role", schema = "erp_over", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_key_role_key", columnNames = {"id_user_key", "tx_role_key"})
})
public class UserRoleDao {

    @EmbeddedId
    private UserRoleId id;

    @MapsId("userKey")
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user_key", nullable = false)
    private UserDao user;

    @MapsId("roleKey")
    @ManyToOne(optional = false)
    @JoinColumn(name = "tx_role_key", nullable = false)
    private RoleDao role;
}