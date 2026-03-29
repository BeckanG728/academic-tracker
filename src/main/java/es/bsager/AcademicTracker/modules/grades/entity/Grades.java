package es.bsager.AcademicTracker.modules.grades.entity;


import es.bsager.AcademicTracker.modules.grades.enums.GradeType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "grades")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grades {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10, updatable = false)
    private GradeType type;

    @Column
    private String notes;

    @Column(nullable = false)
    private UUID subjectId;

    @Column(nullable = false, updatable = false)
    private Instant createAt;

    @Column(nullable = false)
    private Instant updateAt;

    @PrePersist
    protected void onCreate() {
        createAt = Instant.now();
        updateAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = Instant.now();
    }
}
