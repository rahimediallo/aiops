package com.rahim.aiops.application.dto;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
        @NotBlank String question
) {
}