package io.github.edsonisaac.beemonitor.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Set<Mensuration> mensurations;
}