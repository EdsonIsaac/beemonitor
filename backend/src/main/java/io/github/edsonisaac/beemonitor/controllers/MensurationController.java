package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.entities.Mensuration;
import io.github.edsonisaac.beemonitor.services.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * The type Mensuration controller.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping("/mensurations")
public class MensurationController {

    private final FacadeService facade;

    /**
     * Instantiates a new Mensuration controller.
     *
     * @param facade the facade
     */
    @Autowired
    public MensurationController(FacadeService facade) {
        this.facade = facade;
    }

    /**
     * Save response entity.
     *
     * @param code        the code
     * @param mensuration the mensuration
     * @return the response entity
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity save(@RequestPart String code, @RequestPart @Valid Mensuration mensuration) {
        var hive = facade.hiveFindById(facade.hiveFindByCode(code).getId());
        mensuration.setHive(hive);
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.mensurationSave(mensuration));
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
    public ResponseEntity search(@RequestParam(required = false) UUID hiveId, @RequestParam(required = false) Integer size) {

        if (hiveId != null) {

            if (size > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(facade.mensurationFindByHiveIdWithSize(hiveId, size));
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}