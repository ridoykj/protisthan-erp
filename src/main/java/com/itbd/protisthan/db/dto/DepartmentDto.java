package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.DepartmentDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link DepartmentDao}
 */
public record DepartmentDto(
        Long id,
        @NotNull @Size(max = 140) String name,
        @Size(max = 140) String company,
        Instant creation,
        Boolean isDisabled,
        Integer idx,
        Boolean isGroup,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @Size(max = 140) String owner,
        @NotNull DepartmentDao parentDepartment) implements Serializable {

    public static DepartmentDto toDto(DepartmentDao dao) {
        return new DepartmentDto(
                dao.getId(),
                dao.getName(),
                dao.getCompany(),
                dao.getCreation(),
                dao.getIsDisabled(),
                dao.getIdx(),
                dao.getIsGroup(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getOwner(),
                dao.getParentDepartment() == null ? null : dao.getParentDepartment()
        );
    }

    public static void toEntity(DepartmentDto dto,
                                DepartmentDao dao) {
        dao.setId(dto.id());
        dao.setName(dto.name());
        dao.setCompany(dto.company());
        dao.setCreation(dto.creation());
        dao.setIsDisabled(dto.isDisabled());
        dao.setIdx(dto.idx());
        dao.setIsGroup(dto.isGroup());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setOwner(dto.owner());
        dao.setParentDepartment(dto.parentDepartment());
    }
}