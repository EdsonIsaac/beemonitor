package io.github.edsonisaac.beemonitor.dtos;

import io.github.edsonisaac.beemonitor.entities.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * Data Transfer Object (DTO) representing a User.
 * It contains the user's information and provides a conversion method from the User entity to UserDTO.
 *
 * @param id                    the unique identifier of the user
 * @param createdDate           the date and time when the user was created
 * @param lastModifiedDate      the date and time when the user was last modified
 * @param createdByUser         the user who created the user
 * @param modifiedByUser        the user who last modified the user
 * @param name                  the name of the user
 * @param username              the username of the user
 * @param password              the password of the user
 * @param enabled               indicates if the user is enabled
 * @param authorities           the authorities/roles assigned to the user
 * @param accountNonExpired     indicates if the user's account is non-expired
 * @param accountNonLocked      indicates if the user's account is non-locked
 * @param credentialsNonExpired indicates if the user's credentials are non-expired
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
        Collection<?> authorities,
        Boolean accountNonExpired,
        Boolean accountNonLocked,
        Boolean credentialsNonExpired) {

    /**
     * Converts a User entity to a UserDTO object.
     *
     * @param user the User entity to be converted
     * @return the corresponding UserDTO object
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
                user.getAuthorities(),
                user.getAccountNonExpired(),
                user.getAccountNonLocked(),
                user.getCredentialsNonExpired()
        );
    }
}