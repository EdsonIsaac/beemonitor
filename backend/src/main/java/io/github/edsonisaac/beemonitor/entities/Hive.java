package io.github.edsonisaac.beemonitor.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_hives")
public class Hive extends AbstractEntity {

    @NotEmpty
    @Column(name = "code", unique = true)
    private String code;

    @OneToMany(orphanRemoval = true, mappedBy = "hive")
    @JsonManagedReference
    private Set<Mensuration> mensurations;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}