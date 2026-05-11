package com.rahim.aiops.infrastructure.persistence.repository;

import com.rahim.aiops.infrastructure.persistence.entity.IncidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IncidentJpaRepository extends JpaRepository<IncidentEntity, UUID> {
}