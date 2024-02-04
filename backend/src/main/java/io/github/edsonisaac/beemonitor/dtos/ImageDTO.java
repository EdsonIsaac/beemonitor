package io.github.edsonisaac.beemonitor.dtos;

import io.github.edsonisaac.beemonitor.models.Image;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ImageDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String name,
        String path
) implements Serializable {

    public ImageDTO(Image image) {
        this(
            image.getId(), 
            image.getCreatedDate(), 
            image.getLastModifiedDate(), 
            image.getCreatedByUser(), 
            image.getModifiedByUser(), 
            image.getName(),
            image.getPath()
        );
    }
}