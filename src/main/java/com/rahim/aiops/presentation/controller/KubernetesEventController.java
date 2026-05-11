package com.rahim.aiops.presentation.controller;

import com.rahim.aiops.application.dto.KubernetesPodEventRequest;
import com.rahim.aiops.application.service.KubernetesEventService;
import com.rahim.aiops.domain.model.Incident;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kubernetes")
@RequiredArgsConstructor
@Tag(name = "Kubernetes Events", description = "Kubernetes event ingestion APIs")
public class KubernetesEventController {

    private final KubernetesEventService kubernetesEventService;

    @PostMapping("/events")
    @Operation(
            summary = "Receive Kubernetes pod event",
            description = "Convert a Kubernetes pod event into an incident"
    )
    public Incident receivePodEvent(
            @RequestBody KubernetesPodEventRequest request
    ) {

        return kubernetesEventService.convertPodEventToIncident(request);
    }
}