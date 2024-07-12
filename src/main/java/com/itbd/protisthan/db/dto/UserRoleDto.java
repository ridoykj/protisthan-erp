package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.RoleDao;
import com.itbd.protisthan.db.dao.UserDao;
import com.itbd.protisthan.db.dao.UserRoleDao;
import com.itbd.protisthan.db.dao.iddao.UserRoleId;

import java.io.Serializable;

/**
 * DTO for {@link UserRoleDao}
 */
public record UserRoleDto(
        UserRoleId id,
        UserDao user,
        RoleDao role
) implements Serializable {
    public static UserRoleDto toDto(UserRoleDao dao) {
        UserDao user = dao.getUser();
        user.setUserType(null);
        dao.setUser(user);
        return new UserRoleDto(
                dao.getId(),
                dao.getUser(),
                dao.getRole()
        );
    }

    public static void toEntity(UserRoleDto dto, UserRoleDao dao) {
        dao.setId(dto.id());
        dao.setUser(dto.user());
        dao.setRole(dto.role());
    }
}