package io.github.edsonisaac.beemonitor.utils;

import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.projections.UserProjection;

/**
 * The type User utils.
 *
 * @author Edson Isaac
 */
public abstract class UserUtils {

    /**
     * To user.
     *
     * @param projection the projection
     * @return the user
     */
    public static User toUser (UserProjection projection) {

        var user = new User();

        user.setId(projection.getId());
        user.setCreatedDate(projection.getCreatedDate());
        user.setLastModifiedDate(projection.getLastModifiedDate());
        user.setCreatedByUser(projection.getCreatedByUser());
        user.setModifiedByUser(projection.getModifiedByUser());
        user.setName(projection.getName());
        user.setUsername(projection.getUsername());
        user.setPassword(projection.getPassword());
        user.setEnabled(projection.getEnabled());
        user.setDepartment(projection.getDepartment());

        return user;
    }
}