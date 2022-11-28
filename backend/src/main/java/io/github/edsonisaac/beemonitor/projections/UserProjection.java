package io.github.edsonisaac.beemonitor.projections;

import io.github.edsonisaac.beemonitor.enums.Department;

/**
 * The interface User projection.
 *
 * @author Edson Isaac
 */
public interface UserProjection extends AbstractEntityProjection {

    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Gets username.
     *
     * @return the username
     */
    String getUsername();

    /**
     * Gets password.
     *
     * @return the password
     */
    String getPassword();

    /**
     * Get enabled boolean.
     *
     * @return the boolean
     */
    Boolean getEnabled();

    /**
     * Get department.
     *
     * @return the department
     */
    Department getDepartment();
}