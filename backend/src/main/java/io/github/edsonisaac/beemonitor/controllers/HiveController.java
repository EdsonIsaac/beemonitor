package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.dtos.HiveDTO;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.models.Hive;
import io.github.edsonisaac.beemonitor.services.HiveService;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/hives")
@RequiredArgsConstructor
@Tag(name = "Hive", description = "Endpoints for hives management")
public class HiveController implements AbstractController<Hive, HiveDTO> {

    private final HiveService service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public Page<HiveDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "code") String sort,
                                 @RequestParam(required = false, defaultValue = "asc") String direction) {

        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public HiveDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public HiveDTO save(@RequestBody @Valid Hive hive) {
        return service.save(hive);
    }

    @GetMapping("/search")
    @ResponseStatus(OK)
    @Operation(summary = "Search", description = "Search a resource")
    public Page<HiveDTO> search(@RequestParam String value,
                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                @RequestParam(required = false, defaultValue = "10") Integer size,
                                @RequestParam(required = false, defaultValue = "code") String sort,
                                @RequestParam(required = false, defaultValue = "asc") String direction) {

        return service.search(value, page, size, sort, direction);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public HiveDTO update(@PathVariable UUID id, @RequestBody @Valid Hive hive) {

        if (hive.getId().equals(id)) {
            return service.save(hive);
        }

        throw new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND);
    }
}