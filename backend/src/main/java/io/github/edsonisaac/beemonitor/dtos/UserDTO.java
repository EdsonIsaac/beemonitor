package io.github.edsonisaac.beemonitor.dtos;

import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.enums.Department;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type User dto.
 *
 * @author Edson Isaac
 */
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
        Department department
) implements Serializable {

    /**
     * To dto user dto.
     *
     * @param user the user
     * @return the user dto
     */
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
            user.getDepartment()
        );
    }
}