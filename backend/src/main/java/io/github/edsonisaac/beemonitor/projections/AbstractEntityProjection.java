package io.github.edsonisaac.beemonitor.projections;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The interface Abstract entity projection.
 *
 * @author Edson Isaac
 */
public interface AbstractEntityProjection {

    /**
     * Gets id.
     *
     * @return the id
     */
    UUID getId();

    /**
     * Gets created date.
     *
     * @return the created date
     */
    LocalDateTime getCreatedDate();

    /**
     * Gets last modified date.
     *
     * @return the last modified date
     */
    LocalDateTime getLastModifiedDate();

    /**
     * Gets created by user.
     *
     * @return the created by user
     */
    String getCreatedByUser();

    /**
     * Gets modified by user.
     *
     * @return the modified by user
     */
    String getModifiedByUser();
}