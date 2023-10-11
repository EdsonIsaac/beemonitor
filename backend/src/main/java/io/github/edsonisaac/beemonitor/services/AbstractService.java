package io.github.edsonisaac.beemonitor.services;

import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * The AbstractService interface provides a common set of operations for managing resources.
 * Implementations of this interface are responsible for performing CRUD (Create, Read, Update, Delete)
 * operations on the underlying resources, as well as additional validation and business logic specific to the resource type.
 *
 * @param <T> the type of the resource managed by the service
 * @author Edson Isaac
 */
public interface AbstractService<T, DTO> {

    /**
     * Deletes a resource by ID.
     *
     * @param id the ID of the resource to be deleted
     * @throws ObjectNotFoundException if the resource with the given ID is not found
     */
    void delete(UUID id);

    /**
     * Finds all resources.
     *
     * @param page      the page number
     * @param size      the number of resources per page
     * @param sort      the field to sort the resources
     * @param direction the sorting direction ("asc" or "desc")
     * @return A page object containing the list of resources
     */
    Page<DTO> findAll(Integer page, Integer size, String sort, String direction);

    /**
     * Finds a resource by ID.
     *
     * @param id the ID of the resource to be found
     * @return The found resource
     * @throws ObjectNotFoundException if the resource with the given ID is not found
     */
    DTO findById(UUID id);

    /**
     * Saves a resource.
     *
     * @param object the resource to be saved
     * @return The saved resource
     * @throws ValidationException if the resource is not valid
     */
    DTO save(T object);

    /**
     * Validates a resource.
     *
     * @param object the resource to be validated
     * @return true if the resource is valid
     * @throws ValidationException if the resource is not valid
     */
    boolean validate(T object);
}