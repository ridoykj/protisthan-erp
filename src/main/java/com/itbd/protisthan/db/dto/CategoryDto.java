package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.CategoryDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link CategoryDao}
 */
public record CategoryDto(
        Long id,
        @NotNull @Size(max = 15) String name,
        String description,
        String picture
) implements Serializable {

    public static CategoryDto toDto(CategoryDao dao) {
        return new CategoryDto(
                dao.getId(),
                dao.getName(),
                dao.getDescription(),
                dao.getPicture()
        );
    }

    public static void toEntity(CategoryDto dto,
                                CategoryDao dao) {
        dao.setId(dto.id());
        dao.setName(dto.name());
        dao.setDescription(dto.description());
        dao.setPicture(dto.picture());
    }
}
