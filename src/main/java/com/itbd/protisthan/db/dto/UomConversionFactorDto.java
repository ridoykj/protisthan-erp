package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.UomCategoryDao;
import com.itbd.protisthan.db.dao.UomConversionFactorDao;
import com.itbd.protisthan.db.dao.iddao.UomConversionId;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.UomConversionFactorDao}
 */
public record UomConversionFactorDto(
        @NotNull @Id UomConversionId id,
        Instant creation,
        Integer idx,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @Size(max = 140) String owner,
        @NotNull BigDecimal value,
        @NotNull UomCategoryDao category) implements Serializable {

    public static UomConversionFactorDto toDto(UomConversionFactorDao dao) {
        UomCategoryDao category = dao.getCategory();

        if (category != null) category.setIdx(category.getIdx());

        dao.setCategory(category);
        return new UomConversionFactorDto(
                dao.getId(),
                dao.getCreation(),
                dao.getIdx(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getOwner(),
                dao.getValue(),
                dao.getCategory()
//                ,
//                dao.getFromUomKey(),
//                dao.getToUomKey()
        );
    }

    public static void toEntity(UomConversionFactorDto dto,
                                UomConversionFactorDao dao) {
        dao.setCreation(dto.creation());
        dao.setIdx(dto.idx());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setOwner(dto.owner());
        dao.setValue(dto.value());
        dao.setCategory(dto.category());
//        dao.setFromUomKey(dto.fromUomKey());
//        dao.setToUomKey(dto.toUomKey());
    }
}