package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.DocTypeDao;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.DocTypeDao}
 */
public record DocTypeDto(
        @Size(max = 255) String id,
        Instant creation,
        Boolean isCustom,
        String description,
        @Size(max = 140) String documentation,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @Size(max = 140) String module,
        @Size(max = 40) String namingRule) implements Serializable {
    public static DocTypeDto toDto(DocTypeDao dao) {
        return new DocTypeDto(
                dao.getId(),
                dao.getCreation(),
                dao.getIsCustom(),
                dao.getDescription(),
                dao.getDocumentation(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getModule(),
                dao.getNamingRule()
        );
    }

    public static void toEntity(DocTypeDto dto, DocTypeDao dao) {
        dao.setId(dto.id());
        dao.setCreation(dto.creation());
        dao.setIsCustom(dto.isCustom());
        dao.setDescription(dto.description());
        dao.setDocumentation(dto.documentation());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setModule(dto.module());
        dao.setNamingRule(dto.namingRule());
    }
}