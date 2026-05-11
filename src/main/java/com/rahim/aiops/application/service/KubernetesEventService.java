package com.rahim.aiops.application.service;

import com.rahim.aiops.application.dto.KubernetesPodEventRequest;
import com.rahim.aiops.domain.enums.Status;
import com.rahim.aiops.domain.enums.Severity;
import com.rahim.aiops.domain.model.Incident;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KubernetesEventService {

    private final IncidentService incidentService;

    public Incident convertPodEventToIncident(KubernetesPodEventRequest request) {

        Incident incident = new Incident(
                null,
                request.namespace(),
                request.podName(),
                Severity.HIGH,
                Status.OPEN,
                request.reason() + " - " + request.message(),
                null
        );

        return incidentService.createIncident(incident);
    }
}