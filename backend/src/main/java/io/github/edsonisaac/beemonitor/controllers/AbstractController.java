package io.github.edsonisaac.beemonitor.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
     * @return The response entity with the result of the operation
     */
    @Operation(summary = "Delete", description = "Delete an item by ID", responses = {
            @ApiResponse(description = "Success", responseCode = "200"),
            @ApiResponse(description = "Not Found", responseCode = "404"),
            @ApiResponse(description = "Internal Server Error", responseCode = "500")
    })
    ResponseEntity<Void> delete(UUID id);

    /**
     * Find all items.
     *
     * @param page      the page number
     * @param size      the number of items per page
     * @param sort      the field to sort the items
     * @param direction the sorting direction ("asc" or "desc")
     * @return The response entity with the list of items
     */
    @Operation(summary = "Find all", description = "Find all items", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(description = "Internal Server Error", responseCode = "500")
    })
    ResponseEntity<Page<DTO>> findAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String direction);

    /**
     * Find an item by ID.
     *
     * @param id the ID of the item to be found
     * @return The response entity with the found item
     */
    @Operation(summary = "Find by ID", description = "Find an item by ID", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(description = "Not Found", responseCode = "404"),
            @ApiResponse(description = "Internal Server Error", responseCode = "500")
    })
    ResponseEntity<DTO> findById(@PathVariable UUID id);

    /**
     * Save an item.
     *
     * @param object the item to be saved
     * @return The response entity with the saved item
     */
    @Operation(summary = "Save", description = "Save an item", responses = {
            @ApiResponse(description = "Success", responseCode = "201", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal Server Error", responseCode = "500")
    })
    ResponseEntity<DTO> save(@RequestBody T object);

    /**
     * Update an item.
     *
     * @param id     the ID of the item to be updated
     * @param object the updated item
     * @return The response entity with the updated item
     */
    @Operation(summary = "Update", description = "Update an item", responses = {
            @ApiResponse(description = "Success", responseCode = "200", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal Server Error", responseCode = "500")
    })
    ResponseEntity<DTO> update(@PathVariable UUID id, @RequestBody T object);
}