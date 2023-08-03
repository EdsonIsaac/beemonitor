package io.github.edsonisaac.beemonitor.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * An abstract entity class serving as a base for other entities.
 *
 * @author Edson Isaac
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AbstractEntity {

    /**
     * The unique identifier for the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private UUID id;

    /**
     * The timestamp of when the entity was created.
     */
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    /**
     * The timestamp of when the entity was last modified.
     */
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    /**
     * The user who created the entity.
     */
    @CreatedBy
    @Column(name = "created_by_user", updatable = false, length = 50)
    private String createdByUser;

    /**
     * The user who last modified the entity.
     */
    @LastModifiedBy
    @Column(name = "modified_by_user", length = 50)
    private String modifiedByUser;
}