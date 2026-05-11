package com.rahim.aiops.presentation.controller;

import com.rahim.aiops.application.dto.CreateIncidentRequest;
import com.rahim.aiops.application.service.AiIncidentAnalysisService;
import com.rahim.aiops.application.service.IncidentService;
import com.rahim.aiops.domain.model.Incident;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;
    private final AiIncidentAnalysisService aiIncidentAnalysisService;

    @GetMapping
    public Page<Incident> getAll(
            @PageableDefault(
                    size = 10,
                    sort = "createdAt"
            )
            Pageable pageable
    ) {

        return incidentService.getAllIncidents(pageable);
    }

    @PostMapping
    public Incident create(
            @Valid @RequestBody CreateIncidentRequest request
    ) {

        Incident incident = new Incident(
                null,
                request.namespace(),
                request.podName(),
                request.severity(),
                request.status(),
                request.message(),
                null
        );

        return incidentService.createIncident(incident);
    }
    @PostMapping("/analyze")
    public String analyze(@Valid @RequestBody CreateIncidentRequest request) {
        Incident incident = new Incident(
                null,
                request.namespace(),
                request.podName(),
                request.severity(),
                request.status(),
                request.message(),
                null
        );

        return aiIncidentAnalysisService.analyzeIncident(incident);
    }
}