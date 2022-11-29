package io.github.edsonisaac.beemonitor.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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