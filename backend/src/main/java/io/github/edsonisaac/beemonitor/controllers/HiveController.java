package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.services.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

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
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.hiveFindById(id));
    }

    /**
     * Search response entity.
     *
     * @param code the code
     * @return the response entity
     */
    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity search(@RequestParam String code) {

        if (code != null) {
            return ResponseEntity.status(HttpStatus.OK).body(facade.hiveFindByCode(code));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    /**
     * Save response entity.
     *
     * @param hive the hive
     * @return the response entity
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity save(@RequestBody Hive hive) {

        if (hive.getId() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(facade.hiveSave(hive));
        }

        return ResponseEntity.status(HttpStatus.OK).body(facade.hiveSave(hive));
    }

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity delete(@PathVariable UUID id) {
        facade.hiveDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}