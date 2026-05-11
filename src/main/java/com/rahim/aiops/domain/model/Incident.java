package com.rahim.aiops.domain.model;

import com.rahim.aiops.domain.enums.Severity;
import com.rahim.aiops.domain.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.UUID;

public record Incident(
        UUID id,
        String namespace,
        String podName,

        Severity severity,

        Status status,
        String message,
        LocalDateTime detectedAt
) {
}