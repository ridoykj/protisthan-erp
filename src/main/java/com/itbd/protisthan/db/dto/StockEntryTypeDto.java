package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.StockEntryTypeDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.StockEntryTypeDao}
 */
public record StockEntryTypeDto(
        @NotNull @Size(max = 140) String id,
        Boolean isAddToTransit,
        Instant creation,
        Integer idx,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @Size(max = 140) String owner
) implements Serializable {

    public static StockEntryTypeDto toDto(StockEntryTypeDao dao) {
        return new StockEntryTypeDto(
                dao.getId(),
                dao.getIsAddToTransit(),
                dao.getCreation(),
                dao.getIdx(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getOwner()
        );
    }

    public static void toEntity(StockEntryTypeDto dto,
                                StockEntryTypeDao dao) {
        dao.setId(dto.id());
        dao.setIsAddToTransit(dto.isAddToTransit());
        dao.setCreation(dto.creation());
        dao.setIdx(dto.idx());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setOwner(dto.owner());
    }
}