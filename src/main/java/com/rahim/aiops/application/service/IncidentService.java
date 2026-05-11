package com.rahim.aiops.application.service;

import com.rahim.aiops.domain.model.Incident;
import com.rahim.aiops.domain.port.IncidentRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepositoryPort incidentRepositoryPort;

    public Page<Incident> getAllIncidents(Pageable pageable) {
        log.info("Fetching incidents page={}", pageable.getPageNumber());
        return incidentRepositoryPort.findAll(pageable);
    }

    public Incident createIncident(Incident incident) {
        Incident newIncident = new Incident(
                null,
                incident.namespace(),
                incident.podName(),
                incident.severity(),
                incident.status(),
                incident.message(),
                LocalDateTime.now()
        );

        return incidentRepositoryPort.save(newIncident);
    }
}