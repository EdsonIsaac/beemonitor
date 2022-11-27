package io.github.edsonisaac.beemonitor.projections;

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
}