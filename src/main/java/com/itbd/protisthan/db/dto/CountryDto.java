package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.CountryDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.CountryDao}
 */

public record CountryDto(
        @NotNull @Size(max = 16) String id,
        @NotNull String code,
        @NotNull @Size(max = 140) String name,
        Instant creation,
        @NotNull @Size(max = 140) String dateFormat,
        Integer idx,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @Size(max = 140) String owner,
        @NotNull @Size(max = 140) String timeFormat,
        @NotNull String timeZones
) implements Serializable {
    public static CountryDto toDto(CountryDao dao) {
        return new CountryDto(
                dao.getId(),
                dao.getCode(),
                dao.getName(),
                dao.getCreation(),
                dao.getDateFormat(),
                dao.getIdx(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getOwner(),
                dao.getTimeFormat(),
                dao.getTimeZones()
        );
    }

    public static void toEntity(CountryDto dto,
                                CountryDao dao) {
        dao.setId(dto.id());
        dao.setCode(dto.code());
        dao.setName(dto.name());
        dao.setCreation(dto.creation());
        dao.setDateFormat(dto.dateFormat());
        dao.setIdx(dto.idx());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setOwner(dto.owner());
        dao.setTimeFormat(dto.timeFormat());
        dao.setTimeZones(dto.timeZones());
    }
}