package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.ModeOfPaymentDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.ModeOfPaymentDao}
 */
public record ModeOfPaymentDto(
        Long id,
        @NotNull @Size(max = 140) String name,
        Instant creation,
        Boolean isEnabled,
        Integer idx,
//        @Size(max = 140) String modeOfPayment,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @Size(max = 140) String owner,
        @NotNull @Size(max = 140) String type
) implements Serializable {


    public static ModeOfPaymentDto toDto(ModeOfPaymentDao dao) {
        return new ModeOfPaymentDto(
                dao.getId(),
                dao.getName(),
                dao.getCreation(),
                dao.getIsEnabled(),
                dao.getIdx(),
//                dao.getModeOfPayment(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getOwner(),
                dao.getType()
        );
    }

    public static void toEntity(ModeOfPaymentDto dto,
                                ModeOfPaymentDao dao) {
        dao.setName(dto.name());
        dao.setCreation(dto.creation());
        dao.setIsEnabled(dto.isEnabled());
        dao.setIdx(dto.idx());
//        dao.setModeOfPayment(dto.modeOfPayment());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setOwner(dto.owner());
        dao.setType(dto.type());
    }
}