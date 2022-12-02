package io.github.edsonisaac.beemonitor.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * The type Hive.
 *
 * @author Edson Isaac
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_hives")
public class Hive extends AbstractEntity {

    @NotEmpty(message = "{field.code.invalid}")
    private String code;

    @OneToMany(mappedBy = "hive")
    @JsonManagedReference
    private Set<Mensuration> mensurations;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}