package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.dtos.HiveDTO;
import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.services.HiveService;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/hives")
@RequiredArgsConstructor
public class HiveController implements AbstractController<Hive, HiveDTO> {

    private final HiveService service;

    @Override
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @Override
    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public ResponseEntity<Page<HiveDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                                 @RequestParam(required = false, defaultValue = "code") String sort,
                                                 @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var hives = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(HttpStatus.OK).body(hives);
    }

    @Override
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public ResponseEntity<HiveDTO> findById(@PathVariable UUID id) {
        final var hive = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(hive);
    }

    @Override
    @PostMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public ResponseEntity<HiveDTO> save(@RequestBody @Valid Hive hive) {
        final var hiveSaved = service.save(hive);
        return ResponseEntity.status(HttpStatus.CREATED).body(hiveSaved);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<?> search(@RequestParam String value,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer size,
                                    @RequestParam(required = false, defaultValue = "code") String sort,
                                    @RequestParam(required = false, defaultValue = "asc") String direction) {

        final var hives = service.search(value, page, size, sort, direction);
        return ResponseEntity.status(HttpStatus.OK).body(hives);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public ResponseEntity<HiveDTO> update(@PathVariable UUID id, @RequestBody @Valid Hive hive) {

        if (hive.getId().equals(id)) {
            final var hiveSaved = service.save(hive);
            return ResponseEntity.status(HttpStatus.OK).body(hiveSaved);
        }

        throw new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND);
    }
}