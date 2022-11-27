package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.entities.Mensuration;
import io.github.edsonisaac.beemonitor.services.FacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * The type Mensuration controller.
 *
 * @author Edson Isaac
 */
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
    @PostMapping
    public ResponseEntity save(@RequestParam String code, @RequestParam Mensuration mensuration) {
        Hive hive = facade.hiveFindById(facade.hiveFindByCode(code).getId());
        hive.getMensurations().add(mensuration);
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.hiveSave(hive));
    }

    /**
     * Search response entity.
     *
     * @param hiveId the hive id
     * @param size   the size
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity search(@RequestParam(required = false) UUID hiveId, @RequestParam(required = false) Integer size) {

        if (hiveId != null) {

            if (size > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(facade.mensurationFindByHiveIdWithSize(hiveId, size));
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}