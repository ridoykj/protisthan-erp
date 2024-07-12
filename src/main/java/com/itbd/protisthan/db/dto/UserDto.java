package com.itbd.protisthan.db.dto;

import com.itbd.protisthan.db.dao.UserDao;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO for {@link com.itbd.protisthan.db.dao.UserDao}
 */
public record UserDto(
        Long id,
        @Size(max = 140) String name,
        String bio,
        LocalDate birthDate,
        Instant creation,
        @Size(max = 140) String email,
        Boolean isEnabled,
        @Size(max = 140) String fullName,
        Integer ctIdx,
        @Size(max = 140) String lastLogin,
        @Size(max = 140) String location,
        String password,
        @Size(max = 140) String phone,
        String userImage,
        @Size(max = 140) String username) implements Serializable {
    public static UserDto toDto(UserDao dao) {
        return new UserDto(
                dao.getId(),
                dao.getName(),
                dao.getBio(),
                dao.getBirthDate(),
                dao.getCreation(),
                dao.getEmail(),
                dao.getIsEnabled(),
                dao.getFullName(),
                dao.getCtIdx(),
                dao.getLastLogin(),
                dao.getLocation(),
                dao.getPassword(),
                dao.getPhone(),
                dao.getUserImage(),
                dao.getUsername()
        );
    }

    public static void toEntity(UserDto dto, UserDao dao) {
        dao.setId(dto.id());
        dao.setName(dto.name());
        dao.setBio(dto.bio());
        dao.setBirthDate(dto.birthDate());
        dao.setCreation(dto.creation());
        dao.setEmail(dto.email());
        dao.setIsEnabled(dto.isEnabled());
        dao.setFullName(dto.fullName());
        dao.setCtIdx(dto.ctIdx());
        dao.setLastLogin(dto.lastLogin());
        dao.setLocation(dto.location());
        dao.setPassword(dto.password());
        dao.setPhone(dto.phone());
        dao.setUserImage(dto.userImage());
        dao.setUsername(dto.username());
    }
}