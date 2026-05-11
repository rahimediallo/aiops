package com.rahim.aiops.domain.port;

import java.time.LocalDateTime;
import java.util.UUID;

public record Incident(
        UUID id,
        String namespace,
        String podName,
        String severity,
        String status,
        String message,
        LocalDateTime detectedAt
) {
}