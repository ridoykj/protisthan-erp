package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.CountryDao;
import com.itbd.protisthan.db.dao.SupplierDao;
import com.itbd.protisthan.db.dao.SupplierGroupDao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link SupplierDao}
 */
public record SupplierDto(
        Long id,
        @NotNull @Size(max = 40) String companyName,
        @NotNull @Size(max = 30) String contactName,
        @Size(max = 30) String contactTitle,
        @NotNull @Size(max = 60) String address,
        @Size(max = 15) String city,
        @Size(max = 15) String region,
        @Size(max = 10) String postalCode,
        @Size(max = 24) String phone,
        String email,
        @Size(max = 24) String fax,
        String homepage,
        @NotNull SupplierGroupDao supplierGroup,
        @NotNull CountryDao country) implements Serializable {
    public static SupplierDto toDto(SupplierDao dao) {
        return new SupplierDto(
                dao.getId(),
                dao.getCompanyName(),
                dao.getContactName(),
                dao.getContactTitle(),
                dao.getAddress(),
                dao.getCity(),
                dao.getRegion(),
                dao.getPostalCode(),
                dao.getPhone(),
                dao.getEmail(),
                dao.getFax(),
                dao.getHomepage(),
                dao.getSupplierGroup(),
                dao.getCountry()
        );
    }

    public static void toEntity(SupplierDto dto,
                                SupplierDao dao) {
        dao.setId(dto.id());
        dao.setCompanyName(dto.companyName());
        dao.setContactName(dto.contactName());
        dao.setContactTitle(dto.contactTitle());
        dao.setAddress(dto.address());
        dao.setCity(dto.city());
        dao.setRegion(dto.region());
        dao.setPostalCode(dto.postalCode());
        dao.setPhone(dto.phone());
        dao.setEmail(dto.email());
        dao.setFax(dto.fax());
        dao.setHomepage(dto.homepage());
        dao.setSupplierGroup(dto.supplierGroup());
        dao.setCountry(dto.country());
    }
}