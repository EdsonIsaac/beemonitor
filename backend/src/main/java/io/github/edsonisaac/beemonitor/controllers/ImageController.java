package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.dtos.ImageDTO;
import io.github.edsonisaac.beemonitor.entities.Image;
import io.github.edsonisaac.beemonitor.utils.FileUtils;
import io.github.edsonisaac.beemonitor.utils.MediaTypeUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

/**
 * Controller class for handling image-related operations.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Tag(name = "Image", description = "Endpoints for image management")
public class ImageController implements AbstractController<Image, ImageDTO> {

    private final ServletContext servletContext;

    /**
     * Delete an image resource by ID.
     *
     * @param id the ID of the image to delete
     * @return ResponseEntity indicating the result of the operation
     */
    @Operation(summary = "Delete", description = "Delete an resource by ID", responses = {
            @ApiResponse(description = "Internal Server Error", responseCode = "500"),
            @ApiResponse(description = "Fail", responseCode = "501"),
    })
    @Override
    public ResponseEntity<Void> delete(UUID id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    /**
     * Find all images.
     *
     * @param page      the page number
     * @param size      the page size
     * @param sort      the sort field
     * @param direction the sort direction
     * @return ResponseEntity containing a Page of ImageDTO objects
     */
    @Operation(summary = "Find all", description = "Find all resources", responses = {
            @ApiResponse(description = "Internal Server Error", responseCode = "500"),
            @ApiResponse(description = "Fail", responseCode = "501"),
    })
    @Override
    public ResponseEntity<Page<ImageDTO>> findAll(Integer page, Integer size, String sort, String direction) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    /**
     * Find an image by ID.
     *
     * @param id the ID of the image to find
     * @return ResponseEntity containing the ImageDTO
     */
    @Operation(summary = "Find by ID", description = "Find a resource by ID", responses = {
            @ApiResponse(description = "Internal Server Error", responseCode = "500"),
            @ApiResponse(description = "Fail", responseCode = "501"),
    })
    @Override
    public ResponseEntity<ImageDTO> findById(UUID id) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    /**
     * Save an image resource.
     *
     * @param image the image to save
     * @return ResponseEntity containing the saved ImageDTO
     */
    @Operation(summary = "Save", description = "Save a resource", responses = {
            @ApiResponse(description = "Internal Server Error", responseCode = "500"),
            @ApiResponse(description = "Fail", responseCode = "501"),
    })
    @Override
    public ResponseEntity<ImageDTO> save(Image image) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    /**
     * Search for an image by name.
     *
     * @param name the name of the image to search for
     * @return ResponseEntity containing the image file as a resource for download
     * @throws FileNotFoundException if the image file is not found
     */
    @Operation(summary = "Update", description = "Update a resource", responses = {
            @ApiResponse(description = "Success", responseCode = "200"),
            @ApiResponse(description = "Bad Request", responseCode = "400"),
            @ApiResponse(description = "Internal Server Error", responseCode = "500")
    })
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String name) throws FileNotFoundException {

        var file = FileUtils.find(name, FileUtils.IMAGES_DIRECTORY);
        var mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, file.getName());
        var resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }

    /**
     * Update an image resource.
     *
     * @param id    the ID of the image to update
     * @param image the updated image
     * @return ResponseEntity containing the updated ImageDTO
     */
    @Operation(summary = "Update", description = "Update a resource", responses = {
            @ApiResponse(description = "Internal Server Error", responseCode = "500"),
            @ApiResponse(description = "Fail", responseCode = "501")
    })
    @Override
    public ResponseEntity<ImageDTO> update(UUID id, Image image) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}