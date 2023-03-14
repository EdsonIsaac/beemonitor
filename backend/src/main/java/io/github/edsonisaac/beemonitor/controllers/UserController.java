package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.dtos.UserDTO;
import io.github.edsonisaac.beemonitor.entities.Image;
import io.github.edsonisaac.beemonitor.entities.User;
import io.github.edsonisaac.beemonitor.exceptions.ObjectNotFoundException;
import io.github.edsonisaac.beemonitor.services.UserService;
import io.github.edsonisaac.beemonitor.utils.FileUtils;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * The type User controller.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping
    @PreAuthorize("hasRole('SUPPORT')")
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                  @RequestParam(required = false, defaultValue = "name") String sort,
                                  @RequestParam(required = false, defaultValue = "asc") String direction) {

        var users = service.findAll(page, size, sort, direction).map(UserDTO::toDTO);
        return ResponseEntity.status(OK).body(users);
    }

    /**
     * Find by id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPPORT')")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        var user = service.findById(id);
        return ResponseEntity.status(OK).body(UserDTO.toDTO(user));
    }
    
    /**
     * Save response entity.
     *
     * @param user the user
     * @return the response entity
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SUPPORT')")
    public ResponseEntity<?> save(@RequestPart @Valid User user,
                               @RequestPart(required = false) MultipartFile photo) throws FileNotFoundException {

        if (photo != null) {

            var image = Image.builder()
                    .name(System.currentTimeMillis() + "." + FileUtils.getExtension(photo))
                    .build();

            user.setPhoto(image);
            FileUtils.FILES.put(image.getName(), photo);
        }

        user = service.save(user);
        return ResponseEntity.status(CREATED).body(UserDTO.toDTO(user));
    }

    /**
     * Search response entity.
     *
     * @param value     the value
     * @param page      the page
     * @param size      the size
     * @param sort      the sort
     * @param direction the direction
     * @return the response entity
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String value,
                                 @RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "name") String sort,
                                 @RequestParam(required = false, defaultValue = "asc") String direction) {

        var users = service.search(value, page, size, sort, direction).map(UserDTO::toDTO);
        return ResponseEntity.status(OK).body(users);
    }

    /**
     * Update response entity.
     *
     * @param id   the id
     * @param user the user
     * @return the response entity
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SUPPORT')")
    public ResponseEntity<?> update(@PathVariable UUID id,
                                 @RequestPart @Valid User user,
                                 @RequestPart(required = false) MultipartFile photo) throws FileNotFoundException {

        if (user.getId().equals(id)) {

            if (photo != null) {

                var image = Image.builder()
                        .name(System.currentTimeMillis() + "." + FileUtils.getExtension(photo))
                        .build();

                user.setPhoto(image);
                FileUtils.FILES.put(image.getName(), photo);
            }

            user = service.save(user);
            return ResponseEntity.status(OK).body(UserDTO.toDTO(user));
        }

        throw new ObjectNotFoundException(MessageUtils.ARGUMENT_NOT_VALID);
    }
}