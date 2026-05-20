package com.rahim.aiops.application.service;

import com.rahim.aiops.domain.enums.Severity;
import com.rahim.aiops.domain.enums.Status;
import com.rahim.aiops.domain.model.Incident;
import com.rahim.aiops.domain.port.IncidentRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {

    @Mock
    private IncidentRepositoryPort incidentRepositoryPort;

    @InjectMocks
    private IncidentService incidentService;

    @Captor
    private ArgumentCaptor<Incident> incidentCaptor;

    private Incident sampleIncident;

    @BeforeEach
    void setUp() {
        sampleIncident = new Incident(
                UUID.randomUUID(),
                "default",
                "pod-1",
                Severity.CRITICAL,
                Status.OPEN,
                "Pod OOM",
                LocalDateTime.now().minusMinutes(5)
        );
    }

    @Test
    void getAllIncidents_returnsPagedResults() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Incident> page = new PageImpl<>(List.of(sampleIncident), pageable, 1);

        when(incidentRepositoryPort.findAll(any(Pageable.class))).thenReturn(page);

        Page<Incident> result = incidentService.getAllIncidents(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertThat(result.getContent()).containsExactly(sampleIncident);
    }

    @Test
    void createIncident_savesWithNullIdAndDetectedAtSet() {
        // prepare an incident DTO that might have an id/detectedAt set (service should ignore id and set detectedAt)
        Incident input = new Incident(
                UUID.randomUUID(),
                "ns",
                "pod-x",
                Severity.HIGH,
                Status.OPEN,
                "message",
                null
        );

        // mock save to return the received incident back (simulates repository behavior)
        when(incidentRepositoryPort.save(any(Incident.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Incident saved = incidentService.createIncident(input);

        // capture argument passed to repository
        verify(incidentRepositoryPort).save(incidentCaptor.capture());
        Incident passed = incidentCaptor.getValue();

        assertNull(passed.id(), "Saved incident id should be null when creating (id set by persistence layer later)");
        assertNotNull(passed.detectedAt(), "detectedAt should be set by the service");
        assertEquals(input.namespace(), passed.namespace());
        assertEquals(input.podName(), passed.podName());
        assertEquals(input.severity(), passed.severity());
        assertEquals(input.status(), passed.status());
        assertEquals(input.message(), passed.message());

        // the returned value should be the same instance returned by repository mock
        assertNotNull(saved);
        assertNull(saved.id());
        assertNotNull(saved.detectedAt());
    }
}
