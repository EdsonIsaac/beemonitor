package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.repositories.HiveRepository;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * The type Hive service.
 *
 * @author Edson Isaac
 */
@Service
@RequiredArgsConstructor
public class HiveService implements AbstractService<Hive> {

    private final HiveRepository repository;

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {

        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND);
    }

    /**
     * Find all.
     *
     * @return the hive list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Hive> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find by code.
     *
     * @param code the code
     * @return the hive projection
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Hive findByCode(String code) {

        return repository.findByCode(code).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND);
        });
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the hive
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Hive findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND);
        });
    }

    /**
     * Save.
     *
     * @param hive the hive
     * @return the hive
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Hive save(Hive hive) {

        if (hive == null) {
            throw new ValidationException(MessageUtils.HIVE_NULL);
        }

        if (validate(hive)) {
            hive = repository.save(hive);
        }

        return hive;
    }

    /**
     * Search.
     *
     * @param value     the value
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the hive list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Hive> search(String value, Integer page, Integer size, String sort, String direction) {
        return repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Validate.
     *
     * @param hive the hive
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Hive hive) {

        var hive_findByCode = repository.findByCode(hive.getCode());

        if (hive_findByCode.isPresent()) {

            if (!hive_findByCode.get().equals(hive)) {
                throw new ValidationException(MessageUtils.HIVE_ALREADY_SAVE);
            }
        }

        return true;
    }
}