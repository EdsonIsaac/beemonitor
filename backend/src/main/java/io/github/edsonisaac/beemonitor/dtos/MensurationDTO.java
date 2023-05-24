package io.github.edsonisaac.beemonitor.dtos;

import io.github.edsonisaac.beemonitor.entities.Mensuration;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record MensurationDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        Double temperature,
        Double humidity,
        Double weight
) implements Serializable {

    public static MensurationDTO toDTO(Mensuration mensuration) {

        return new MensurationDTO(
            mensuration.getId(),
            mensuration.getCreatedDate(),
            mensuration.getLastModifiedDate(),
            mensuration.getCreatedByUser(),
            mensuration.getModifiedByUser(),
            mensuration.getTemperature(),
            mensuration.getHumidity(),
            mensuration.getWeight()
        );
    }
}