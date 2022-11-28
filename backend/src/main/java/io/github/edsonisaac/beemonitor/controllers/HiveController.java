package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.services.FacadeService;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * The type Hive controller.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping(value = "/hives")
public class HiveController {

    private final FacadeService facade;

    /**
     * Instantiates a new Hive controller.
     *
     * @param facade the facade
     */
    @Autowired
    public HiveController(FacadeService facade) {
        this.facade = facade;
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/{id}")
    @RolesAllowed({"ROLE_ADMINISTRATION", "ROLE_SUPPORT"})
    public ResponseEntity delete(@PathVariable UUID id) {
        facade.hiveDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    @RolesAllowed({"ROLE_ADMINISTRATION", "ROLE_SUPPORT"})
    public ResponseEntity findAll () {
        return ResponseEntity.status(HttpStatus.OK).body(facade.hiveFindAll());
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping(value = "/{id}")
    @RolesAllowed({"ROLE_ADMINISTRATION", "ROLE_SUPPORT"})
    public ResponseEntity findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.hiveFindById(id));
    }

    /**
     * Save response entity.
     *
     * @param hive the hive
     * @return the response entity
     */
    @PostMapping
    @RolesAllowed({"ROLE_ADMINISTRATION", "ROLE_SUPPORT"})
    public ResponseEntity save(@RequestBody @Valid Hive hive) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.hiveSave(hive));
    }

    /**
     * Search response entity.
     *
     * @param code the code
     * @return the response entity
     */
    @GetMapping(value = "/search")
    public ResponseEntity search(@RequestParam String code) {

        if (code != null) {
            return ResponseEntity.status(HttpStatus.OK).body(facade.hiveFindByCode(code));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Update response entity.
     *
     * @param id   the id
     * @param hive the hive
     * @return the response entity
     */
    @PutMapping("/{id}")
    @RolesAllowed({"ROLE_ADMINISTRATION", "ROLE_SUPPORT"})
    public ResponseEntity update(@PathVariable UUID id, @RequestBody @Valid Hive hive) {

        if (hive.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(facade.hiveSave(hive));
        }

        throw new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND);
    }
}