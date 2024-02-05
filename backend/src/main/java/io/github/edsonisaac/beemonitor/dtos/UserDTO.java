package io.github.edsonisaac.beemonitor.dtos;

import io.github.edsonisaac.beemonitor.enums.Department;
import io.github.edsonisaac.beemonitor.models.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String name,
        String username,
        String password,
        Boolean enabled,
        Department department,
        ImageDTO photo
) implements Serializable {

    public UserDTO(User user) {
        this(
            user.getId(),
            user.getCreatedDate(),
            user.getLastModifiedDate(),
            user.getCreatedByUser(),
            user.getModifiedByUser(),
            user.getName(),
            user.getUsername(),
            user.getPassword(),
            user.getEnabled(),
            user.getDepartment(),
            user.getPhoto() != null ? new ImageDTO(user.getPhoto()) : null
        );
    }
}