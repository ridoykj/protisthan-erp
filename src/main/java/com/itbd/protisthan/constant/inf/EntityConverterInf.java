package com.itbd.protisthan.constant.inf;

public interface EntityConverterInf<Entity, DTO> {
    DTO toDto(Entity entity);

    Entity toEntity(DTO dto);
}
