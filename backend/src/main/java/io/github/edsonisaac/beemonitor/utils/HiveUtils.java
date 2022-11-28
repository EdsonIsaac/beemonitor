package io.github.edsonisaac.beemonitor.utils;

import io.github.edsonisaac.beemonitor.entities.Hive;
import io.github.edsonisaac.beemonitor.projections.HiveProjection;

/**
 * The type Hive utils.
 *
 * @author Edson Isaac
 */
public abstract class HiveUtils {

    /**
     * To hive.
     *
     * @param projection the projection
     * @return the hive
     */
    public static Hive toHive (HiveProjection projection) {

        var hive = new Hive();

        hive.setId(projection.getId());
        hive.setCreatedDate(projection.getCreatedDate());
        hive.setLastModifiedDate(projection.getLastModifiedDate());
        hive.setCreatedByUser(projection.getCreatedByUser());
        hive.setModifiedByUser(projection.getModifiedByUser());
        hive.setCode(projection.getCode());

        return hive;
    }
}