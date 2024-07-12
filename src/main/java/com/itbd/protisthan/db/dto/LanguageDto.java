package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.LanguageDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.LanguageDao}
 */
public record LanguageDto(
        @NotNull @Size(max = 16) String id,
        Instant creation,
        Boolean isEnabled,
        @Size(max = 140) String flag,
        Integer idx,
        @NotNull  @Size(max = 140) String code,
        @NotNull  @Size(max = 140) String name,
        Instant modified,
        @Size(max = 140) String modifiedBy
) implements Serializable {

    public static LanguageDto toDto(LanguageDao dao) {
        return new LanguageDto(
                dao.getId(),
                dao.getCreation(),
                dao.getIsEnabled(),
                dao.getFlag(),
                dao.getIdx(),
                dao.getCode(),
                dao.getName(),
                dao.getModified(),
                dao.getModifiedBy()
        );
    }

    public static void toEntity(LanguageDto dto,
                                LanguageDao dao) {
        dao.setId(dto.id());
        dao.setCreation(dto.creation());
        dao.setIsEnabled(dto.isEnabled());
        dao.setFlag(dto.flag());
        dao.setIdx(dto.idx());
        dao.setCode(dto.code());
        dao.setName(dto.name());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
    }
}