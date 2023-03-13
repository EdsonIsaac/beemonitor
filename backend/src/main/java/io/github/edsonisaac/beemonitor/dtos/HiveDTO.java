package io.github.edsonisaac.beemonitor.dtos;

import io.github.edsonisaac.beemonitor.entities.Hive;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


/**
 * The type Hive dto.
 *
 * @author Edson Isaac
 */
public record HiveDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String code,
        Set<MensurationDTO> mensurations
) implements Serializable {

    /**
     * To dto hive dto.
     *
     * @param hive the hive
     * @return the hive dto
     */
    public static HiveDTO toDTO(Hive hive) {

        return new HiveDTO(
            hive.getId(),
            hive.getCreatedDate(),
            hive.getLastModifiedDate(),
            hive.getCreatedByUser(),
            hive.getModifiedByUser(),
            hive.getCode(),
            null
        );
    }
}