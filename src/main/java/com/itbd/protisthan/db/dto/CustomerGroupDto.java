package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.CustomerGroupDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.CustomerGroupDao}
 */
public record CustomerGroupDto(
        Long id,
        Instant creation,
        @NotNull @Size(max = 140) String name,
        @Size(max = 140) String defaultPriceList,
        Integer idx,
        @NotNull  Boolean isGroup,
        Instant modified,
        @Size(max = 140) String modifiedBy,
        @Size(max = 140) String owner,
        @Size(max = 140) String paymentTerms
) implements Serializable {

    public static CustomerGroupDto toDto(CustomerGroupDao dao) {
        return new CustomerGroupDto(
                dao.getId(),
                dao.getCreation(),
                dao.getName(),
                dao.getDefaultPriceList(),
                dao.getIdx(),
                dao.getIsGroup(),
                dao.getModified(),
                dao.getModifiedBy(),
                dao.getOwner(),
                dao.getPaymentTerms()
        );
    }

    public static void toEntity(CustomerGroupDto dto,
                                CustomerGroupDao dao) {
        dao.setId(dto.id());
        dao.setCreation(dto.creation());
        dao.setName(dto.name());
        dao.setDefaultPriceList(dto.defaultPriceList());
        dao.setIdx(dto.idx());
        dao.setIsGroup(dto.isGroup());
        dao.setModified(dto.modified());
        dao.setModifiedBy(dto.modifiedBy());
        dao.setOwner(dto.owner());
        dao.setPaymentTerms(dto.paymentTerms());
    }
}