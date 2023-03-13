package io.github.edsonisaac.beemonitor.controllers;

import io.github.edsonisaac.beemonitor.utils.FileUtils;
import io.github.edsonisaac.beemonitor.utils.MediaTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.springframework.http.HttpStatus.OK;

/**
 * The type Image controller.
 *
 * @author Edson Isaac
 */
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ServletContext servletContext;

    /**
     * Search response entity.
     *
     * @param name the name
     * @return the response entity
     * @throws FileNotFoundException the file not found exception
     */
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String name) throws FileNotFoundException {

        var file = FileUtils.find(name, FileUtils.IMAGES_DIRECTORY);
        var mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, file.getName());
        var resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity
                .status(OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(mediaType)
                .contentLength(file.length())
                .body(resource);
    }
}