package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.UserTypeDao;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.UserTypeDao}
 */
public record UserTypeDto(
        @Size(max = 140) String id,
        Instant creation,
        Integer idx,
        Boolean isStandard,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @Size(max = 140) String owner,
        @Size(max = 140) String role) implements Serializable {
    public static UserTypeDto toDto(UserTypeDao dao) {
        return new UserTypeDto(
                dao.getId(),
                dao.getCreation(),
                dao.getIdx(),
                dao.getIsStandard(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getOwner(),
                dao.getRole()
        );
    }

    public static void toEntity(UserTypeDto dto, UserTypeDao dao) {
        dao.setId(dto.id());
        dao.setCreation(dto.creation());
        dao.setIdx(dto.idx());
        dao.setIsStandard(dto.isStandard());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setOwner(dto.owner());
        dao.setRole(dto.role());
    }
}