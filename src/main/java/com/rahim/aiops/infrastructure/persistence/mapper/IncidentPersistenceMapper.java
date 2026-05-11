package com.rahim.aiops.infrastructure.persistence.mapper;

import com.rahim.aiops.domain.model.Incident;
import com.rahim.aiops.infrastructure.persistence.entity.IncidentEntity;

public class IncidentPersistenceMapper {

    public static Incident toDomain(IncidentEntity entity) {
        return new Incident(
                entity.getId(),
                entity.getNamespace(),
                entity.getPodName(),
                entity.getSeverity(),
                entity.getStatus(),
                entity.getMessage(),
                entity.getDetectedAt()
        );
    }

    public static IncidentEntity toEntity(Incident incident) {
        return IncidentEntity.builder()
                .id(incident.id())
                .namespace(incident.namespace())
                .podName(incident.podName())
                .severity(incident.severity())
                .status(incident.status())
                .message(incident.message())
                .detectedAt(incident.detectedAt())
                .build();
    }
}