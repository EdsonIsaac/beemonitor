package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.dtos.HiveDTO;
import io.github.edsonisaac.beemonitor.models.Hive;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.repositories.HiveRepository;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HiveService implements AbstractService<Hive, HiveDTO> {

    private final HiveRepository repository;

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
    public Page<HiveDTO> findAll(Integer page, Integer size, String sort, String direction) {
        final var hives = repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
        return hives.map(HiveDTO::new);
    }

    @Transactional(readOnly = true)
    public HiveDTO findByCode(String code) {
        final var hive = repository.findByCode(code).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND));
        return new HiveDTO(hive);
    }

    @Override
    @Transactional(readOnly = true)
    public HiveDTO findById(UUID id) {
        final var hive = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.HIVE_NOT_FOUND));
        return new HiveDTO(hive);
    }

    @Override
    @Transactional
    public HiveDTO save(Hive hive) {

        if (hive == null) {
            throw new ValidationException(MessageUtils.HIVE_NULL);
        }

        if (validate(hive)) {
            hive = repository.save(hive);
        }
        
        return new HiveDTO(hive);
    }

    @Transactional(readOnly = true)
    public Page<HiveDTO> search(String value, Integer page, Integer size, String sort, String direction) {
        final var hives = repository.search(value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
        return hives.map(HiveDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validate(Hive hive) {
        
        final var hive_findByCode = repository.findByCode(hive.getCode());

        if (hive_findByCode.isPresent() && !hive_findByCode.get().equals(hive)) {
            throw new ValidationException(MessageUtils.HIVE_ALREADY_SAVE);
        }

        return true;
    }
}