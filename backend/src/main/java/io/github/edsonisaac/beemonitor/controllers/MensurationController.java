package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.dtos.MensurationDTO;
import io.github.edsonisaac.beemonitor.entities.Mensuration;
import io.github.edsonisaac.beemonitor.services.HiveService;
import io.github.edsonisaac.beemonitor.services.MensurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * The type Mensuration controller.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping("/mensurations")
@RequiredArgsConstructor
public class MensurationController {

    private final HiveService hiveService;
    private final MensurationService mensurationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATION', 'SUPPORT')")
    public ResponseEntity<?> findAll(@RequestParam UUID hiveId,
                                     @RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer size,
                                     @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                     @RequestParam(required = false, defaultValue = "desc") String direction) {

        var mensurations = mensurationService.findAll(hiveId, page, size, sort, direction).map(MensurationDTO::toDTO);
        return ResponseEntity.status(OK).body(mensurations);
    }

    /**
     * Save response entity.
     *
     * @param code        the code
     * @param mensuration the mensuration
     * @return the response entity
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity save(@RequestPart String code,
                               @RequestPart @Valid Mensuration mensuration) {

        var hive = hiveService.findByCode(code);
        mensuration.setHive(hive);
        mensuration = mensurationService.save(mensuration);

        return ResponseEntity.status(CREATED).body(MensurationDTO.toDTO(mensuration));
    }

    /**
     * Search response entity.
     *
     * @param hiveId the hive id
     * @param size   the size
     * @return the response entity
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMINISTRATION', 'SUPPORT')")
    public ResponseEntity search(@RequestParam UUID hiveId,
                                 @RequestParam String date,
                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "createdDate") String sort,
                                 @RequestParam(required = false, defaultValue = "desc") String direction) {

        var mensurations = mensurationService.search(hiveId, date, page, size, sort, direction).map(MensurationDTO::toDTO);
        return ResponseEntity.status(OK).body(mensurations);
    }
}