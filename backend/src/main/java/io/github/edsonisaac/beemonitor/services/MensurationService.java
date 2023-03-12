package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.entities.Mensuration;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.repositories.MensurationRepository;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

/**
 * The type Mensuration service.
 *
 * @author Edson Isaac
 */
@Service
@RequiredArgsConstructor
public class MensurationService implements AbstractService<Mensuration> {

    private final MensurationRepository repository;

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

        throw new ObjectNotFoundException(MessageUtils.MENSURATION_NOT_FOUND);
    }

    /**
     * Find all.
     *
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the mensuration list
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Mensuration> findAll(Integer page, Integer size, String sort, String direction) {
        return repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find all.
     *
     * @param hiveId    the hive id
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the mensuration list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Mensuration> findAll(UUID hiveId, Integer page, Integer size, String sort, String direction) {
        return repository.findAll(hiveId, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the mensuration
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Mensuration findById(UUID id) {

        return repository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException(MessageUtils.MENSURATION_NOT_FOUND);
        });
    }

    /**
     * Save.
     *
     * @param mensuration the mensuration
     * @return the mensuration
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Mensuration save(Mensuration mensuration) {

        if (mensuration == null) {
            throw new ValidationException(MessageUtils.MENSURATION_NULL);
        }

        if (validate(mensuration)) {
            mensuration = repository.save(mensuration);
        }

        return mensuration;
    }

    /**
     * Search.
     *
     * @param hiveId    the hive id
     * @param date      the date
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the mensuration list
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Mensuration> search(UUID hiveId, String date, Integer page, Integer size, String sort, String direction) {
        return repository.search(hiveId, date, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
    }

    /**
     * Validate.
     *
     * @param mensuration the mensuration
     * @return the boolean
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean validate(Mensuration mensuration) {


        return true;
    }
}