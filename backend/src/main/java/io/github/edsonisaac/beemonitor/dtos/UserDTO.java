package io.github.edsonisaac.beemonitor.dtos;

import io.github.edsonisaac.beemonitor.entities.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
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
        Collection<?> authorities
) implements Serializable {

    public static UserDTO toDTO(User user) {

        return new UserDTO(
                user.getId(),
                user.getCreatedDate(),
                user.getLastModifiedDate(),
                user.getCreatedByUser(),
                user.getModifiedByUser(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAuthorities());
    }
}