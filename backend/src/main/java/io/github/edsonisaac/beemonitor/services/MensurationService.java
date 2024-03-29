package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.dtos.MensurationDTO;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.models.Mensuration;
import io.github.edsonisaac.beemonitor.repositories.MensurationRepository;
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
public class MensurationService implements AbstractService<Mensuration, MensurationDTO> {

    private final MensurationRepository repository;

    @Override
    @Transactional
    public void delete(UUID id) {

        if (id != null) {

            if (repository.existsById(id)) {
                repository.deleteById(id);
                return;
            }
        }

        throw new ObjectNotFoundException(MessageUtils.MENSURATION_NOT_FOUND);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MensurationDTO> findAll(Integer page, Integer size, String sort, String direction) {
        final var mensurations = repository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
        return mensurations.map(MensurationDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public MensurationDTO findById(UUID id) {
        final var mensuration = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(MessageUtils.MENSURATION_NOT_FOUND));
        return new MensurationDTO(mensuration);
    }

    @Override
    @Transactional
    public MensurationDTO save(Mensuration mensuration) {

        if (mensuration == null) {
            throw new ValidationException(MessageUtils.MENSURATION_NULL);
        }

        if (validate(mensuration)) {
            mensuration = repository.save(mensuration);
        }
        
        return new MensurationDTO(mensuration);
    }

    @Transactional(readOnly = true)
    public Page<MensurationDTO> search(UUID hiveId, String value, Integer page, Integer size, String sort, String direction) {
        final var mensurations = repository.search(hiveId, value, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));
        return mensurations.map(MensurationDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validate(Mensuration mensuration) {
        return true;
    }
}