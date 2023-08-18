package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.dtos.MensurationDTO;
import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.entities.Mensuration;
import io.github.edsonisaac.beemonitor.services.HiveService;
import io.github.edsonisaac.beemonitor.services.MensurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/mensurations")
@RequiredArgsConstructor
@Tag(name = "Mensuration", description = "Endpoints for mensurations management")
public class MensurationController implements AbstractController<Mensuration, MensurationDTO> {

    private final HiveService hiveService;
    private final MensurationService mensurationService;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public void delete(@PathVariable UUID id) {
        mensurationService.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public Page<MensurationDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size,
                                        @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                        @RequestParam(required = false, defaultValue = "desc") String direction) {

        return mensurationService.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public MensurationDTO findById(@PathVariable UUID id) {
        return mensurationService.findById(id);
    }

    @Override
    public MensurationDTO save(Mensuration mensuration) {
        return mensurationService.save(mensuration);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    public MensurationDTO save(@RequestPart String code, @RequestPart @Valid Mensuration mensuration) {
        
        final var hiveDTO = hiveService.findByCode(code);
        final var hive = new Hive();
        
        BeanUtils.copyProperties(hiveDTO, hive);
        mensuration.setHive(hive);

        return save(mensuration);
    }

    @GetMapping("/search")
    @ResponseStatus(OK)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    @Operation(summary = "Search", description = "Search a resource")
    public Page<MensurationDTO> search(@RequestParam UUID hiveId,
                                       @RequestParam String value,
                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                       @RequestParam(required = false, defaultValue = "10") Integer size,
                                       @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                       @RequestParam(required = false, defaultValue = "desc") String direction) {

        return mensurationService.search(hiveId, value, page, size, sort, direction);
    }

    @Override
    @PutMapping("/{id}")
    @ResponseStatus(NOT_IMPLEMENTED)
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMINISTRATION', 'SCOPE_SUPPORT')")
    public MensurationDTO update(@PathVariable UUID id, @RequestBody @Valid Mensuration mensuration) {
        return null;
    }
}