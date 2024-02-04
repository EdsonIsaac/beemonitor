package io.github.edsonisaac.beemonitor.models;

import io.github.edsonisaac.beemonitor.listeners.ImageListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_images")
@EntityListeners(ImageListener.class)
public class Image extends AbstractModel {

    @NotEmpty
    @Column(name = "name", unique = true, length = 25)
    private String name;

    @NotEmpty
    @Column(name = "path")
    private String path;
}