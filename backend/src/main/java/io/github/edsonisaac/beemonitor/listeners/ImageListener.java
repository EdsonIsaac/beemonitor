package io.github.edsonisaac.beemonitor.listeners;

import io.github.edsonisaac.beemonitor.models.Image;
import io.github.edsonisaac.beemonitor.services.AWSS3Service;
import jakarta.persistence.PreRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageListener  {

    @Autowired
    private AWSS3Service awss3Service;

    @PreRemove
    private void preRemove(Image image) {
        awss3Service.delete(image.getName());
    }
}