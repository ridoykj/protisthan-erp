package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.SupplierGroupDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.SupplierGroupDao}
 */
public record SupplierGroupDto(
        Long id,
        @NotNull @Size(max = 140) String name,
        Instant creation,
        Integer idx,
        Boolean group,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @NotNull SupplierGroupDao parentSupplierGroup) implements Serializable {

    public static SupplierGroupDto toDto(SupplierGroupDao dao) {
        return new SupplierGroupDto(
                dao.getId(),
                dao.getName(),
                dao.getCreation(),
                dao.getIdx(),
                dao.getGroup(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getParentSupplierGroup() == null ? null : dao.getParentSupplierGroup()
        );
    }

    public static void toEntity(SupplierGroupDto dto,
                                SupplierGroupDao dao) {
        dao.setId(dto.id());
        dao.setName(dto.name());
        dao.setCreation(dto.creation());
        dao.setIdx(dto.idx());
        dao.setGroup(dto.group());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setParentSupplierGroup(dto.parentSupplierGroup());
    }
}