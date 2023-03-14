package io.github.edsonisaac.beemonitor.entities;

import io.github.edsonisaac.beemonitor.exceptions.OperationFailureException;
import io.github.edsonisaac.beemonitor.utils.FileUtils;
import io.github.edsonisaac.beemonitor.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;

/**
 * The type Image.
 *
 * @author Edson Isaac
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_images")
public class Image extends AbstractEntity {

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @PostPersist
    @PostUpdate
    private void postSave() {

        FileUtils.FILES.forEach((key, value) -> {

            try {
                FileUtils.save(key, value, FileUtils.IMAGES_DIRECTORY);
            } catch (IOException e) {
                throw new OperationFailureException(MessageUtils.OPERATION_FAILURE);
            }
        });

        FileUtils.FILES.clear();
    }

    @PostRemove
    private void postDelete() {
        FileUtils.delete(name, FileUtils.IMAGES_DIRECTORY);
    }
}