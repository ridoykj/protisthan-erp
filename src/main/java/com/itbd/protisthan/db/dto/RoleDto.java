package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.RoleDao;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.RoleDao}
 */
public record RoleDto(
        @Size(max = 140) String id,
        Boolean isBulkActions,
        Instant creation,
        Boolean isDashboard,
        Boolean isDeskAccess,
        Boolean isDisabled,
        @Size(max = 140) String homePage,
        Integer idx,
        Instant modified,
        Boolean isNotifications,
        @Size(max = 140) String restrictToDomain,
        Boolean twoFactorAuth) implements Serializable {
    public static RoleDto toDto(RoleDao dao) {
        return new RoleDto(
                dao.getId(),
                dao.getIsBulkActions(),
                dao.getCreation(),
                dao.getIsDashboard(),
                dao.getIsDeskAccess(),
                dao.getIsDisabled(),
                dao.getHomePage(),
                dao.getIdx(),
                dao.getModified(),
                dao.getIsNotifications(),
                dao.getRestrictToDomain(),
                dao.getTwoFactorAuth()
        );
    }

    public static void toEntity(RoleDto dto, RoleDao dao) {
        dao.setId(dto.id());
        dao.setIsBulkActions(dto.isBulkActions());
        dao.setCreation(dto.creation());
        dao.setIsDashboard(dto.isDashboard());
        dao.setIsDeskAccess(dto.isDeskAccess());
        dao.setIsDisabled(dto.isDisabled());
        dao.setHomePage(dto.homePage());
        dao.setIdx(dto.idx());
        dao.setModified(dto.modified());
        dao.setIsNotifications(dto.isNotifications());
        dao.setRestrictToDomain(dto.restrictToDomain());
        dao.setTwoFactorAuth(dto.twoFactorAuth());
    }
}