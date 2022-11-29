package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.services.FacadeService;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * The type User controller.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final FacadeService facade;

    /**
     * Instantiates a new User controller.
     *
     * @param facade the facade
     */
    @Autowired
    public UserController(FacadeService facade) {
        this.facade = facade;
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    @PreAuthorize("hasRole('SUPPORT')")
    public ResponseEntity findAll() {
        var users = facade.userFindAll().stream().filter(user -> !user.getUsername().equals("cooperativa")).toList();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    /**
     * Save response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasRole('SUPPORT')")
    public ResponseEntity save(@RequestBody @Valid User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.userSave(user));
    }

    /**
     * Update response entity.
     *
     * @param id   the id
     * @param user the user
     * @return the response entity
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPPORT')")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody @Valid User user) {

        if (user.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(facade.userSave(user));
        }

        throw new ObjectNotFoundException(MessageUtils.USER_NOT_FOUND);
    }
}