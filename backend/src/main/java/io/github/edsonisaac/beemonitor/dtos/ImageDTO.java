package io.github.edsonisaac.beemonitor.dtos;

import io.github.edsonisaac.beemonitor.entities.Image;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The type Image dto.
 *
 * @author Edson Isaac
 */
public record ImageDTO(
        UUID id,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate,
        String createdByUser,
        String modifiedByUser,
        String name
) implements Serializable {

    /**
     * To dto image dto.
     *
     * @param image the image
     * @return the image dto
     */
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