package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.UomCategoryDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.UomCategoryDao}
 */
public record UomCategoryDto(
        @NotNull @Size(max = 140) String id,
        Instant creation,
        Integer idx,
        Instant modified,
        @Size(max = 140) String modifiedBy
) implements Serializable {

    public static UomCategoryDto toDto(UomCategoryDao dao) {
        return new UomCategoryDto(
                dao.getId(),
                dao.getCreation(),
                dao.getIdx(),
                dao.getModified(),
                dao.getModifiedBy()
        );
    }

    public static void toEntity(UomCategoryDto dto,
                                UomCategoryDao dao) {
        dao.setId(dto.id());
        dao.setCreation(dto.creation());
        dao.setIdx(dto.idx());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
    }
}