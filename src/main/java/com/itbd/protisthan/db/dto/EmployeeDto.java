package com.itbd.protisthan.db.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itbd.protisthan.constant.enums.BloodGroupEnum;
import com.itbd.protisthan.constant.enums.ReligionEnum;
import com.itbd.protisthan.db.dao.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link EmployeeDao}
 */
public record EmployeeDto(
        Long id,
        @NotNull @Size(max = 20) String employeeId,
        @NotNull @Size(max = 64) String firstName,
        @NotNull @Size(max = 64) String lastName,
        @Size(max = 30) String title,
        @Size(max = 30) String fatherName,
        @Size(max = 30) String motherName,
        LocalDate birthDate,
        BloodGroupEnum bloodGroup,
        @NotNull GenderDao gender,
        ReligionEnum religion,
        @Size(max = 30) String nationality,
        @Size(max = 30) String nid,
        @Size(max = 64) String passportNumber,
        @Size(max = 16) String maritalStatus,
        @Size(max = 30) String spouseName,
        Integer children,
        @Size(max = 127) String permanentAddress,
        @Size(max = 127) String presentAddress,
        @Size(max = 25) String titleOfCourtesy,
        @NotNull LocalDate hireDate,
        @Size(max = 15) String city,
        @Size(max = 15) String region,
        @Size(max = 10) String postalCode,
        @NotNull CountryDao country,
        LanguageDao language,

        @Size(max = 24) String phone,
        @Size(max = 24) String telephone,
        @Size(max = 100) String email,
        @Size(max = 4) String extension,
        String photo,
        String notes,
        @Size(max = 255) String photoPath,
        @Size(max = 32) String bankAcNo,
        @Size(max = 100) String bankName,
        LocalDate dateOfRetirement,
        @NotNull  DesignationDao designation,
        @NotNull DepartmentDao department,
        LocalDate heldOn,
        @Size(max = 64) String iban,
        @Size(max = 256) String image,
        NameSeriesDao namingSeries,
        @Size(max = 256) String reasonForLeaving,
        @Size(max = 4) String salaryCurrency,
        @Size(max = 32) String salaryMode,
        BigDecimal salary,
        Boolean isDisabled,
        String bio) implements Serializable {
    public static EmployeeDto toDto(EmployeeDao dao) {
        GenderDao gender = dao.getGender();
        CountryDao country = dao.getCountry();
        LanguageDao language = dao.getLanguage();
        NameSeriesDao namingSeries = dao.getNamingSeries();
        DepartmentDao department = dao.getDepartment();
        DesignationDao designation = dao.getDesignation();

        if (gender != null) gender.setIdx(gender.getIdx());
        if (country != null) country.setIdx(country.getIdx());
        if (language != null) language.setIdx(language.getIdx());
        if (namingSeries != null) namingSeries.setIdx(namingSeries.getIdx());
        if (department != null) department.setIdx(department.getIdx());
        if (designation != null) designation.setIdx(designation.getIdx());

        dao.setGender(gender);
        dao.setCountry(country);
        dao.setLanguage(language);
        dao.setNamingSeries(namingSeries);
        dao.setDepartment(department);
        dao.setDesignation(designation);

        return new EmployeeDto(
                dao.getId(),
                dao.getEmployeeId(),
                dao.getFirstName(),
                dao.getLastName(),
                dao.getTitle(),
                dao.getFatherName(),
                dao.getMotherName(),
                dao.getBirthDate(),
                dao.getBloodGroup(),
                dao.getGender(),
                dao.getReligion(),
                dao.getNationality(),
                dao.getNid(),
                dao.getPassportNumber(),
                dao.getMaritalStatus(),
                dao.getSpouseName(),
                dao.getChildren(),
                dao.getPermanentAddress(),
                dao.getPresentAddress(),
                dao.getTitleOfCourtesy(),
                dao.getHireDate(),
                dao.getCity(),
                dao.getRegion(),
                dao.getPostalCode(),
                dao.getCountry(),
                dao.getLanguage(),
                dao.getPhone(),
                dao.getTelephone(),
                dao.getEmail(),
                dao.getExtension(),
                dao.getPhoto(),
                dao.getNotes(),
                dao.getPhotoPath(),
                dao.getBankAcNo(),
                dao.getBankName(),
                dao.getDateOfRetirement(),
                dao.getDesignation(),
                dao.getDepartment(),
                dao.getHeldOn(),
                dao.getIban(),
                dao.getImage(),
                dao.getNamingSeries(),
                dao.getReasonForLeaving(),
                dao.getSalaryCurrency(),
                dao.getSalaryMode(),
                dao.getSalary(),
                dao.getIsDisabled(),
                dao.getBio()
        );
    }

    public static void toEntity(EmployeeDto dto,
                                EmployeeDao dao) {
        dao.setEmployeeId(dto.employeeId());
        dao.setFirstName(dto.firstName());
        dao.setLastName(dto.lastName());
        dao.setTitle(dto.title());
        dao.setFatherName(dto.fatherName());
        dao.setMotherName(dto.motherName());
        dao.setBirthDate(dto.birthDate());
        dao.setBloodGroup(dto.bloodGroup());
        dao.setGender(dto.gender());
        dao.setReligion(dto.religion());
        dao.setNationality(dto.nationality());
        dao.setNid(dto.nid());
        dao.setPassportNumber(dto.passportNumber());
        dao.setMaritalStatus(dto.maritalStatus());
        dao.setSpouseName(dto.spouseName());
        dao.setChildren(dto.children());
        dao.setPermanentAddress(dto.permanentAddress());
        dao.setPresentAddress(dto.presentAddress());
        dao.setTitleOfCourtesy(dto.titleOfCourtesy());
        dao.setHireDate(dto.hireDate());
        dao.setCity(dto.city());
        dao.setRegion(dto.region());
        dao.setPostalCode(dto.postalCode());
        dao.setCountry(dto.country());
        dao.setLanguage(dto.language());
        dao.setPhone(dto.phone());
        dao.setTelephone(dto.telephone());
        dao.setEmail(dto.email());
        dao.setExtension(dto.extension());
        dao.setPhoto(dto.photo());
        dao.setNotes(dto.notes());
        dao.setPhotoPath(dto.photoPath());
        dao.setBankAcNo(dto.bankAcNo());
        dao.setBankName(dto.bankName());
        dao.setDateOfRetirement(dto.dateOfRetirement());
        dao.setDesignation(dto.designation());
        dao.setDepartment(dto.department());
        dao.setHeldOn(dto.heldOn());
        dao.setIban(dto.iban());
        dao.setImage(dto.image());
        dao.setNamingSeries(dto.namingSeries());
        dao.setReasonForLeaving(dto.reasonForLeaving());
        dao.setSalaryCurrency(dto.salaryCurrency());
        dao.setSalaryMode(dto.salaryMode());
        dao.setSalary(dto.salary());
        dao.setIsDisabled(dto.isDisabled());
        dao.setBio(dto.bio());
    }
}