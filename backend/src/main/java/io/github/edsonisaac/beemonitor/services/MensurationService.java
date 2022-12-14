package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.entities.Mensuration;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.projections.MensurationProjection;
import io.github.edsonisaac.beemonitor.repositories.MensurationRepository;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * The type Mensuration service.
 *
 * @author Edson Isaac
 */
@Service
public class MensurationService {

    private final MensurationRepository repository;

    /**
     * Instantiates a new Mensuration service.
     *
     * @param repository the repository
     */
    @Autowired
    public MensurationService(MensurationRepository repository) {
        this.repository = repository;
    }

    /**
     * Find all by hive list.
     *
     * @param hiveId the hive id
     * @return the list
     */
    @Transactional
    public void deleteByHive(UUID hiveId) {

        if (hiveId == null) {
            throw new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND);
        }

        if (repository.existsByHive(hiveId)) {
            repository.deleteByHive(hiveId);
        }
    }

    /**
     * Find by hive id with size list.
     *
     * @param hiveId the hive id
     * @param size   the size
     * @return the list
     */
    public List<MensurationProjection> findByHiveIdWithSize(UUID hiveId, Integer size) {
        var pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        return repository.findByHiveId(hiveId, pageable).getContent();
    }

    /**
     * Save mensuration.
     *
     * @param mensuration the mensuration
     * @return the mensuration
     */
    public Mensuration save(Mensuration mensuration) {

        if (mensuration == null) {
            throw new ValidationException(MessageUtils.MENSURATION_NULL);
        }

        return repository.save(mensuration);
    }
}