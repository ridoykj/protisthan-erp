package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.constant.enums.BloodGroupEnum;
import com.itbd.protisthan.constant.enums.ReligionEnum;
import com.itbd.protisthan.db.dao.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link CustomerDao}
 */
public record CustomerDto(
        Long id,
        @NotNull @Size(max = 20) String customerId,
        @NotNull @Size(max = 64) String firstName,
        @NotNull @Size(max = 64) String lastName,
        LocalDate birthDate,
        @Size(max = 64) String companyName,
        @Size(max = 30) String title,
        BloodGroupEnum bloodGroup,
        @NotNull GenderDao gender,
        ReligionEnum religion,
        @Size(max = 124) String homeAddress,
        @Size(max = 124) String officeAddress,
        @Size(max = 15) String city,
        @Size(max = 15) String region,
        @Size(max = 10) String postalCode,
        @NotNull CountryDao country,
        @Size(max = 24) String phone,
        @Size(max = 24) String telephone,
        @Size(max = 100) String email,
        @Size(max = 24) String fax,
        @Size(max = 256) String customerDetails,
        Boolean isDisabled,
        @Size(max = 256) String image,
        @Size(max = 16) String maritalStatus,
        @Size(max = 30) String spouseName,
        Integer children,
        NameSeriesDao namingSeries,
        @Size(max = 128) String taxId,
        @Size(max = 30) String nid,
        @Size(max = 140) String territory,
        String notes
) implements Serializable {
    public static CustomerDto toDto(CustomerDao dao) {
        GenderDao gender = dao.getGender();
        CountryDao country = dao.getCountry();
        LanguageDao language = dao.getLanguage();
        NameSeriesDao namingSeries = dao.getNamingSeries();

        if (gender != null) gender.setIdx(gender.getIdx());
        if (country != null) country.setIdx(country.getIdx());
        if (language != null) language.setIdx(language.getIdx());
        if (namingSeries != null) namingSeries.setIdx(namingSeries.getIdx());

        dao.setGender(gender);
        dao.setCountry(country);
        dao.setLanguage(language);
        dao.setNamingSeries(namingSeries);
        return new CustomerDto(
                dao.getId(),
                dao.getCustomerId(),
                dao.getFirstName(),
                dao.getLastName(),
                dao.getBirthDate(),
                dao.getCompanyName(),
                dao.getTitle(),
                dao.getBloodGroup(),
                dao.getGender(),
                dao.getReligion(),
                dao.getHomeAddress(),
                dao.getOfficeAddress(),
                dao.getCity(),
                dao.getRegion(),
                dao.getPostalCode(),
                dao.getCountry(),
                dao.getPhone(),
                dao.getTelephone(),
                dao.getEmail(),
                dao.getFax(),
                dao.getCustomerDetails(),
                dao.getIsDisabled(),
                dao.getImage(),
                dao.getMaritalStatus(),
                dao.getSpouseName(),
                dao.getChildren(),
                dao.getNamingSeries(),
                dao.getTaxId(),
                dao.getNid(),
                dao.getTerritory(),
                dao.getNotes()
        );
    }

    public static void toEntity(CustomerDto dto,
                                CustomerDao dao) {
        dao.setCustomerId(dto.customerId());
        dao.setFirstName(dto.firstName());
        dao.setLastName(dto.lastName());
        dao.setBirthDate(dto.birthDate());
        dao.setCompanyName(dto.companyName());
        dao.setTitle(dto.title());
        dao.setBloodGroup(dto.bloodGroup());
        dao.setGender(dto.gender());
        dao.setReligion(dto.religion());
        dao.setHomeAddress(dto.homeAddress());
        dao.setOfficeAddress(dto.officeAddress());
        dao.setCity(dto.city());
        dao.setRegion(dto.region());
        dao.setPostalCode(dto.postalCode());
        dao.setCountry(dto.country());
        dao.setPhone(dto.phone());
        dao.setTelephone(dto.telephone());
        dao.setEmail(dto.email());
        dao.setFax(dto.fax());
        dao.setCustomerDetails(dto.customerDetails());
        dao.setIsDisabled(dto.isDisabled() != null ? dto.isDisabled() : dao.getIsDisabled());
        dao.setImage(dto.image());
        dao.setMaritalStatus(dto.maritalStatus());
        dao.setSpouseName(dto.spouseName());
        dao.setChildren(dto.children());
        dao.setNamingSeries(dto.namingSeries());
        dao.setTaxId(dto.taxId());
        dao.setNid(dto.nid());
        dao.setTerritory(dto.territory());
        dao.setNotes(dto.notes());
    }
}