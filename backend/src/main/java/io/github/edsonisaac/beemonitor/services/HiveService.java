package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.dtos.HiveDTO;
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
 * The HiveService class provides service methods for managing Hive entities.
 * It implements the AbstractService interface for generic CRUD operations.
 * This class handles hive-related data and business logic in the system.
 *
 * @author Edson Isaac
 */
@Service
@RequiredArgsConstructor
public class HiveService implements AbstractService<Hive, HiveDTO> {

    private final HiveRepository repository;

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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<HiveDTO> findAll(Integer page, Integer size, String sort, String direction) {
        final var hives = repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
        return hives.map(HiveDTO::toDTO);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public HiveDTO findByCode(String code) {
        final var hive = repository.findByCode(code).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND));
        return HiveDTO.toDTO(hive);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public HiveDTO findById(UUID id) {
        final var hive = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND));
        return HiveDTO.toDTO(hive);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public HiveDTO save(Hive hive) {

        if (hive == null) {
            throw new ValidationException(MessageUtils.HIVE_NULL);
        }

        hive = repository.save(hive);
        return HiveDTO.toDTO(hive);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<HiveDTO> search(String value, Integer page, Integer size, String sort, String direction) {
        final var hives = repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
        return hives.map(HiveDTO::toDTO);
    }
}