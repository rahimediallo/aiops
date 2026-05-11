package com.rahim.aiops.application.dto;

import com.rahim.aiops.domain.enums.Severity;
import com.rahim.aiops.domain.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateIncidentRequest(
        @NotBlank String namespace,
        @NotBlank String podName,
        @NotNull Severity severity,
        @NotNull Status status,
        @NotBlank String message
) {
}