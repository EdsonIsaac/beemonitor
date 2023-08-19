package io.github.edsonisaac.beemonitor.dtos;

import io.github.edsonisaac.beemonitor.entities.Image;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ImageDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String name
) implements Serializable {

    public static ImageDTO toDTO(Image image) {

        return new ImageDTO(
                image.getId(),
                image.getCreatedDate(),
                image.getLastModifiedDate(),
                image.getCreatedByUser(),
                image.getModifiedByUser(),
                image.getName()
        );
    }
}