package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.projections.HiveProjection;
import io.github.edsonisaac.beemonitor.repositories.HiveRepository;
import io.github.edsonisaac.beemonitor.utils.HiveUtils;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The type Hive service.
 *
 * @author Edson Isaac
 */
@Service
public class HiveService {

    private final HiveRepository repository;

    /**
     * Instantiates a new Hive service.
     *
     * @param repository the repository
     */
    @Autowired
    public HiveService(HiveRepository repository) {
        this.repository = repository;
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    public List<HiveProjection> findAll() {
        return repository.findAlll();
    }

    /**
     * Find by id hive.
     *
     * @param id the id
     * @return the hive
     */
    public Hive findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND);
        });
    }

    /**
     * Find by code hive projection.
     *
     * @param code the code
     * @return the hive projection
     */
    public HiveProjection findByCode(String code) {

        return repository.findByCode(code).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND);
        });
    }

    /**
     * Save hive.
     *
     * @param hive the hive
     * @return the hive
     */
    public Hive save(Hive hive) {

        if (hive == null) {
            throw new ValidationException(MessageUtils.HIVE_NULL);
        }

        if (validateHive(hive)) {
            hive = repository.save(hive);
        }

        return hive;
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    public void delete(UUID id) {

        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND);
    }

    private boolean validateHive(Hive hive) {

        var hive_findByCode = repository.findByCode(hive.getCode());

        if (hive_findByCode.isPresent()) {

            if (!hive.equals(HiveUtils.toHive(hive_findByCode.get()))) {
                throw new ValidationException(MessageUtils.HIVE_ALREADY_SAVE);
            }
        }

        return true;
    }
}