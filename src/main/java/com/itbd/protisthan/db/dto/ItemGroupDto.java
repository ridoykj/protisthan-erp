package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.ItemGroupDao;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.ItemGroupDao}
 */
public record ItemGroupDto(
        Long id,
        @Size(max = 140) String name,
        Instant creation,
        Integer idx,
        String image,
        Boolean isGroup,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        ItemGroupDao parentItemGroup) implements Serializable {
    public static ItemGroupDto toDto(ItemGroupDao dao) {
        return new ItemGroupDto(
                dao.getId(),
                dao.getName(),
                dao.getCreation(),
                dao.getIdx(),
                dao.getImage(),
                dao.getIsGroup(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getParentItemGroup()
        );
    }

    public static void toEntity(ItemGroupDto dto, ItemGroupDao dao) {
        dao.setId(dto.id());
        dao.setName(dto.name());
        dao.setCreation(dto.creation());
        dao.setIdx(dto.idx());
        dao.setImage(dto.image());
        dao.setIsGroup(dto.isGroup());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setParentItemGroup(dto.parentItemGroup());
    }
}