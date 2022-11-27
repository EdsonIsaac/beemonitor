package io.github.edsonisaac.beemonitor.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Mensuration.
 *
 * @author Edson Isaac
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_mensurations")
public class Mensuration extends AbstractEntity {

    @NotNull(message = "{field.temperature.invalid}")
    @Column(updatable = false)
    private Double temperature;

    @NotNull(message = "{field.humidity.invalid}")
    @Column(updatable = false)
    private Double humidity;

    @NotNull(message = "{field.weight.invalid}")
    @Column(updatable = false)
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "hive_id")
    private Hive hive;
}