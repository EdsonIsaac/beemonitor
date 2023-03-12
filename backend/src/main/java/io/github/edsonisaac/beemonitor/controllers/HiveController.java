package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.dtos.HiveDTO;
import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.services.HiveService;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Hive controller.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping(value = "/hives")
@RequiredArgsConstructor
public class HiveController {

    private final HiveService service;

    /**
     * Delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATION', 'SUPPORT')")
    public ResponseEntity delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(OK).body(null);
    }

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATION', 'SUPPORT')")
    public ResponseEntity findAll (@RequestParam(required = false, defaultValue = "0") Integer page,
                                   @RequestParam(required = false, defaultValue = "10") Integer size,
                                   @RequestParam(required = false, defaultValue = "code") String sort,
                                   @RequestParam(required = false, defaultValue = "asc") String direction) {

        var hives = service.findAll(page, size, sort, direction).map(HiveDTO::toDTO);
        return ResponseEntity.status(OK).body(hives);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATION', 'SUPPORT')")
    public ResponseEntity findById(@PathVariable UUID id) {
        var hive = service.findById(id);
        return ResponseEntity.status(OK).body(HiveDTO.toDTO(hive));
    }

    /**
     * Save response entity.
     *
     * @param hive the hive
     * @return the response entity
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATION', 'SUPPORT')")
    public ResponseEntity save(@RequestBody @Valid Hive hive) {
        hive = service.save(hive);
        return ResponseEntity.status(CREATED).body(HiveDTO.toDTO(hive));
    }


    /**
     * Search response entity.
     *
     * @param value     the value
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the response entity
     */
    @GetMapping(value = "/search")
    public ResponseEntity search(@RequestParam String value,
                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "name") String sort,
                                 @RequestParam(required = false, defaultValue = "asc") String direction) {

        var hives = service.search(value, page, size, sort, direction).map(HiveDTO::toDTO);
        return ResponseEntity.status(OK).body(hives);
    }

    /**
     * Update response entity.
     *
     * @param id   the id
     * @param hive the hive
     * @return the response entity
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATION', 'SUPPORT')")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody @Valid Hive hive) {

        if (hive.getId().equals(id)) {
            hive = service.save(hive);
            return ResponseEntity.status(OK).body(HiveDTO.toDTO(hive));
        }

        throw new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND);
    }
}