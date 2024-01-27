package io.github.edsonisaac.beemonitor.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_hives")
public class Hive extends AbstractModel {

    @NotEmpty
    @Column(name = "code", unique = true, length = 50)
    private String code;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "hive")
    @JsonManagedReference
    private Set<Mensuration> mensurations;
}