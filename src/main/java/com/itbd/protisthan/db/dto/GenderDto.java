package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.GenderDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.GenderDao}
 */
public record GenderDto(
        Long id,
       @NotNull @Size(max = 140) String name,
        Instant creation,
        Integer idx,
        Instant modified,
        @Size(max = 140) String modifiedBy
) implements Serializable {
    public static GenderDto toDto(GenderDao dao) {
        return new GenderDto(
                dao.getId(),
                dao.getName(),
                dao.getCreation(),
                dao.getIdx(),
                dao.getModified(),
                dao.getModifiedBy()
        );
    }

    public static void toEntity(GenderDto dto,
                                GenderDao dao) {
        dao.setName(dto.name());
        dao.setCreation(dto.creation());
        dao.setIdx(dto.idx());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
    }
}