package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.dtos.UserDTO;
import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.services.UserService;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * REST controller for managing users.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Endpoints for users management")
public class UserController implements AbstractController<User, UserDTO> {

    private final UserService service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public Page<UserDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "name") String sort,
                                 @RequestParam(required = false, defaultValue = "asc") String direction) {

        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public UserDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public UserDTO save(@RequestBody User user) {
        return service.save(user);
    }

    @GetMapping("/search")
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    @Operation(summary = "Search", description = "Search a resource")
    public Page<UserDTO> search(@RequestParam String value,
                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                @RequestParam(required = false, defaultValue = "10") Integer size,
                                @RequestParam(required = false, defaultValue = "code") String sort,
                                @RequestParam(required = false, defaultValue = "asc") String direction) {

        return service.search(value, page, size, sort, direction);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public UserDTO update(@PathVariable UUID id, @RequestBody User user) {

        if (user.getId().equals(id)) {
            return service.save(user);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}