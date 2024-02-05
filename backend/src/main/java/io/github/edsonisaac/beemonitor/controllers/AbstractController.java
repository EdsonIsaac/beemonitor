package io.github.edsonisaac.beemonitor.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * The AbstractController interface provides a common set of operations for CRUD (Create, Read, Update, Delete) operations
 * on resources. Implementations of this interface are responsible for handling HTTP requests and performing the
 * corresponding operations on the underlying resources.
 *
 * @param <T> the type of the resource managed by the controller
 * @author Edson Isaac
 */
public interface AbstractController<T, DTO> {

    /**
     * Delete an item by ID.
     *
     * @param id the ID of the item to be deleted
     */
    @Operation(summary = "Delete", description = "Delete an item by ID")
    void delete(UUID id);

    /**
     * Find all items.
     *
     * @param page      the page number
     * @param size      the number of items per page
     * @param sort      the field to sort the items
     * @param direction the sorting direction ("asc" or "desc")
     * @return The list of items
     */
    @Operation(summary = "Find all", description = "Find all items")
    Page<DTO> findAll(Integer page, Integer size, String sort, String direction);

    /**
     * Find an item by ID.
     *
     * @param id the ID of the item to be found
     * @return The found item
     */
    @Operation(summary = "Find by ID", description = "Find an item by ID")
    DTO findById(UUID id);

    /**
     * Save an item.
     *
     * @param object the item to be saved
     * @return The saved item
     */
    @Operation(summary = "Save", description = "Save an item")
    DTO save(T object);

    /**
     * Update an item.
     *
     * @param id     the ID of the item to be updated
     * @param object the updated item
     * @return The updated item
     */
    @Operation(summary = "Update", description = "Update an item")
    DTO update(UUID id, T object);
}