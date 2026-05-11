package com.rahim.aiops.presentation.controller;

import com.rahim.aiops.application.service.IncidentService;
import com.rahim.aiops.domain.enums.Severity;
import com.rahim.aiops.domain.enums.Status;
import com.rahim.aiops.domain.model.Incident;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulations")
@RequiredArgsConstructor
@Tag(name = "Kubernetes Events", description = "Kubernetes incident simulation endpoints")
public class SimulationController {

    private final IncidentService incidentService;

    @PostMapping("/crashloop")
    @Operation(
            summary = "Simulate CrashLoopBackOff",
            description = "Generate a fake Kubernetes CrashLoopBackOff incident for demo/testing"
    )
    public Incident simulateCrashLoopBackOff() {

        Incident incident = new Incident(
                null,
                "prod",
                "payment-service",
                Severity.HIGH,
                Status.OPEN,
                "CrashLoopBackOff - Back-off restarting failed container",
                null
        );

        return incidentService.createIncident(incident);
    }
}