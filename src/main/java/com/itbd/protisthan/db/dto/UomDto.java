package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.UomDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.UomDao}
 */
public record UomDto(
        @NotNull @Size(max = 140) String id,
        Instant creation,
        Boolean isEnabled,
        Integer idx,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        Boolean isMustBeWholeNumber,
        @Size(max = 140) String owner) implements Serializable {

    public static UomDto toDto(UomDao dao) {
        return new UomDto(
                dao.getId(),
                dao.getCreation(),
                dao.getIsEnabled(),
                dao.getIdx(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getIsMustBeWholeNumber(),
                dao.getOwner()
        );
    }

    public static void toEntity(UomDto dto,
                                UomDao dao) {
        dao.setId(dto.id());
        dao.setCreation(dto.creation());
        dao.setIsEnabled(dto.isEnabled());
        dao.setIdx(dto.idx());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setIsMustBeWholeNumber(dto.isMustBeWholeNumber());
        dao.setOwner(dto.owner());
    }
}