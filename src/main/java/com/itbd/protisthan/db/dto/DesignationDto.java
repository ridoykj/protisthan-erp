package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.DesignationDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.DesignationDao}
 */
public record DesignationDto(
        Long id,
        Instant creation,
        String description,
       @NotNull @Size(max = 140) String name,
        @Size(max = 16) String shortName,
        Integer idx,
        Instant modified,
        @Size(max = 140) String modifiedBy
) implements Serializable {
    public static DesignationDto toDto(DesignationDao dao) {
        return new DesignationDto(
                dao.getId(),
                dao.getCreation(),
                dao.getDescription(),
                dao.getName(),
                dao.getShortName(),
                dao.getIdx(),
                dao.getModified(),
                dao.getModifiedBy()
        );
    }

    public static void toEntity(DesignationDto dto,
                                DesignationDao dao) {
        dao.setId(dto.id());
        dao.setCreation(dto.creation());
        dao.setDescription(dto.description());
        dao.setName(dto.name());
        dao.setShortName(dto.shortName());
        dao.setIdx(dto.idx());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
    }
}