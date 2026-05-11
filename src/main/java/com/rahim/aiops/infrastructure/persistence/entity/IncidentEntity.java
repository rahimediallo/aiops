package com.rahim.aiops.infrastructure.persistence.entity;

import com.rahim.aiops.domain.enums.Severity;
import com.rahim.aiops.domain.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "incidents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "namespace_name")
    private String namespace;

    private String podName;
    @Enumerated(EnumType.STRING)
    private Severity severity;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length = 5000)
    private String message;

    private LocalDateTime detectedAt;
}