package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.constant.enums.NameSeriesEnum;
import com.itbd.protisthan.db.dao.NameSeriesDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.NameSeriesDao}
 */
public record NameSeriesDto(
        Long id,
        Instant creation,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @NotNull @Size(max = 16) String shortName,
        @NotNull @Size(max = 140) String name,
        String description,
        @NotNull NameSeriesEnum type,
        Integer idx
) implements Serializable {

    public static NameSeriesDto toDto(NameSeriesDao dao) {
        return new NameSeriesDto(
                dao.getId(),
                dao.getCreation(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getShortName(),
                dao.getName(),
                dao.getDescription(),
                dao.getType(),
                dao.getIdx()
        );
    }

    public static void toEntity(NameSeriesDto dto,
                                NameSeriesDao dao) {
        dao.setId(dto.id());
        dao.setCreation(dto.creation());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setShortName(dto.shortName());
        dao.setName(dto.name());
        dao.setDescription(dto.description());
        dao.setType(dto.type());
        dao.setIdx(dto.idx());
    }
}