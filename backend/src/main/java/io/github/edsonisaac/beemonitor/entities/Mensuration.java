package io.github.edsonisaac.beemonitor.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_mensurations")
public class Mensuration extends AbstractEntity {

    @NotNull
    @Column(name = "temperature", updatable = false)
    private Double temperature;

    @NotNull
    @Column(name = "humidity", updatable = false)
    private Double humidity;

    @NotNull
    @Column(name = "weight", updatable = false)
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "hive_id")
    @JsonBackReference
    private Hive hive;
}