package com.rahim.aiops.application.service;

import com.rahim.aiops.application.dto.KubernetesPodEventRequest;
import com.rahim.aiops.domain.enums.Severity;
import com.rahim.aiops.domain.enums.Status;
import com.rahim.aiops.domain.model.Incident;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KubernetesEventServiceTest {

    @Mock
    private IncidentService incidentService;

    @InjectMocks
    private KubernetesEventService kubernetesEventService;

    @Captor
    private ArgumentCaptor<Incident> incidentCaptor;

    @BeforeEach
    void setUp() {
    }

    @Test
    void convertPodEventToIncident_createsHighSeverityOpenIncident_andDelegatesToIncidentService() {
        KubernetesPodEventRequest req = new KubernetesPodEventRequest(
                "default",
                "pod-42",
                "CrashLoopBackOff",
                "back-off restarting",
                "Failed"
        );

        // simulate repository returning an incident with an id
        Incident returned = new Incident(UUID.randomUUID(), req.namespace(), req.podName(), Severity.HIGH, Status.OPEN, req.reason() + " - " + req.message(), LocalDateTime.now());
        when(incidentService.createIncident(any(Incident.class))).thenReturn(returned);

        Incident result = kubernetesEventService.convertPodEventToIncident(req);

        verify(incidentService).createIncident(incidentCaptor.capture());
        Incident passed = incidentCaptor.getValue();

        assertNull(passed.id());
        assertEquals(req.namespace(), passed.namespace());
        assertEquals(req.podName(), passed.podName());
        assertEquals(Severity.HIGH, passed.severity());
        assertEquals(Status.OPEN, passed.status());
        assertEquals(req.reason() + " - " + req.message(), passed.message());

        // returned should be the mocked returned incident
        assertThat(result).isEqualTo(returned);
    }
}
