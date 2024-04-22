package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.dtos.UserDTO;
import io.github.edsonisaac.beemonitor.exceptions.ValidationException;
import io.github.edsonisaac.beemonitor.models.Image;
import io.github.edsonisaac.beemonitor.models.User;
import io.github.edsonisaac.beemonitor.services.AWSS3Service;
import io.github.edsonisaac.beemonitor.services.UserService;
import io.github.edsonisaac.beemonitor.utils.FileUtils;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "Endpoints for users management")
public class UserController implements AbstractController<User, UserDTO> {

    private final UserService service;
    private final AWSS3Service awss3Service;

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @Override
    @GetMapping
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public Page<UserDTO> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size,
                                 @RequestParam(required = false, defaultValue = "name") String sort,
                                 @RequestParam(required = false, defaultValue = "asc") String direction) {

        return service.findAll(page, size, sort, direction);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public UserDTO findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @Override
    public UserDTO save(User user) {
        return null;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(CREATED)
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public UserDTO save(@RequestPart User user,
                     @RequestPart(required = false) MultipartFile photo) {
        handlePhoto(user, photo);
        return service.save(user);
    }

    @GetMapping("/search")
    @ResponseStatus(OK)
    @Operation(summary = "Search", description = "Search a resource")
    public Page<UserDTO> search(@RequestParam String value,
                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                @RequestParam(required = false, defaultValue = "10") Integer size,
                                @RequestParam(required = false, defaultValue = "name") String sort,
                                @RequestParam(required = false, defaultValue = "asc") String direction) {

        return service.search(value, page, size, sort, direction);
    }

    @Override
    public UserDTO update(UUID id, User user) {
        return null;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(OK)
    @PreAuthorize("hasAuthority('SCOPE_SUPPORT')")
    public UserDTO update(@PathVariable UUID id,
                          @RequestPart @Valid User user,
                          @RequestPart(required = false) MultipartFile photo) {

        if (user.getId().equals(id)) {
            handlePhoto(user, photo);
            return service.save(user);
        }

        throw new ValidationException(MessageUtils.ARGUMENT_NOT_VALID);
    }

    @SneakyThrows
    private void handlePhoto(User user, MultipartFile photo) {

        final var file = FileUtils.save(photo, FileUtils.FILES_DIRECTORY);
        final var path = awss3Service.save(file);
        final var image = Image.builder()
                .name(file.getName())
                .path(path)
                .build();

        user.setPhoto(image);
        FileUtils.delete(file.getName(), FileUtils.FILES_DIRECTORY);
    }
}