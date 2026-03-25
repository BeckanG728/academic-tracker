package es.bsager.AcademicTracker.modules.grades.entity;


import es.bsager.AcademicTracker.modules.grades.enums.GradeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "grades")
@Getter
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
    @Column(nullable = false, length = 10)
    private GradeType type;

    @Column(nullable = true)
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
