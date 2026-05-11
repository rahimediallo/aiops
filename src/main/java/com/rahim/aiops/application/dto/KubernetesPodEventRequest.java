package com.rahim.aiops.application.dto;

public record KubernetesPodEventRequest(
        String namespace,
        String podName,
        String reason,
        String message,
        String status
) {
}