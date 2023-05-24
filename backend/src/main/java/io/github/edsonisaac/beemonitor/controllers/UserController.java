package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.dtos.UserDTO;
import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for managing users.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Endpoints for users management")
public class UserController implements AbstractController<User, UserDTO> {

    private final UserService service;

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @return Response entity with no content in the response body
     */
    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Retrieves all users with pagination and sorting options.
     *
     * @param page       the page number (optional, default: 0)
     * @param size       the page size (optional, default: 10)
     * @param sort       the sort field (optional, default: "name")
     * @param direction  the sort direction (optional, default: "asc")
     * @return ResponseEntity with a page of UserDTO objects in the response body
     */
    @Override
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public ResponseEntity<Page<UserDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                                 @RequestParam(required = false, defaultValue = "name") String sort,
                                                 @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var users = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return ResponseEntity with the UserDTO object in the response body
     */
    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id) {
        final var user = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    /**
     * Saves a new user.
     *
     * @param user the User object to save
     * @return ResponseEntity with the saved UserDTO object in the response body
     */
    @Override
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public ResponseEntity<UserDTO> save(@RequestBody User user) {
        final var userSaved = service.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSaved);
    }

    /**
     * Updates an existing user by ID.
     *
     * @param id   the ID of the user to update
     * @param user the updated User object
     * @return ResponseEntity with the updated UserDTO object in the response body
     * @throws ValidationException if the provided ID is invalid
     */
    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public ResponseEntity<UserDTO> update(@PathVariable UUID id, @RequestBody User user) {

        if (user.getId().equals(id)) {
            final var userUpdated = service.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
        }

        throw new ValidationException("ID inválido!");
    }
}