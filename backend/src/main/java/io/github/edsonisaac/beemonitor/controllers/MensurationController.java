package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.dtos.MensurationDTO;
import io.github.edsonisaac.beemonitor.entities.Mensuration;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.services.MensurationService;
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
@RequestMapping("/mensurations")
@RequiredArgsConstructor
public class MensurationController implements AbstractController<Mensuration, MensurationDTO> {

    private final MensurationService service;

    @Override
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public ResponseEntity<Page<MensurationDTO>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                                        @RequestParam(required = false, defaultValue = "desc") String direction) {

        final var mensurations = service.findAll(page, size, sort, direction);
        return ResponseEntity.status(HttpStatus.OK).body(mensurations);
    }

    @Override
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public ResponseEntity<MensurationDTO> findById(@PathVariable UUID id) {
        final var mensuration = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(mensuration);
    }

    @PostMapping
    public ResponseEntity<MensurationDTO> save(@RequestBody @Valid Mensuration mensuration) {
        final var mensurationSaved = service.save(mensuration);
        return ResponseEntity.status(HttpStatus.CREATED).body(mensurationSaved);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public ResponseEntity<Page<MensurationDTO>> search(@RequestParam UUID hiveId,
                                                       @RequestParam(required = false) String date,
                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                                       @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                                       @RequestParam(required = false, defaultValue = "desc") String direction) {

        if (hiveId != null) {

            if (date != null) {
                final var mensurations = service.search(hiveId, date, page, size, sort, direction);
                return ResponseEntity.status(HttpStatus.OK).body(mensurations);
            }

            var mensurations = service.search(hiveId, page, size, sort, direction);
            return ResponseEntity.status(HttpStatus.OK).body(mensurations);
        }

        throw new ObjectNotFoundException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    @Override
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public ResponseEntity<MensurationDTO> update(@PathVariable UUID id, @RequestBody @Valid Mensuration mensuration) {

        if (mensuration.getId().equals(id)) {
            final var mensurationSaved = service.save(mensuration);
            return ResponseEntity.status(HttpStatus.CREATED).body(mensurationSaved);
        }

        throw new ObjectNotFoundException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}