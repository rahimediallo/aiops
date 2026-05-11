package com.rahim.aiops.infrastructure.persistence.repository;

import com.rahim.aiops.domain.model.Incident;
import com.rahim.aiops.domain.port.IncidentRepositoryPort;
import com.rahim.aiops.infrastructure.persistence.mapper.IncidentPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class IncidentRepositoryAdapter implements IncidentRepositoryPort {

    private final IncidentJpaRepository incidentJpaRepository;

    @Override
    public List<Incident> findAll() {
        return incidentJpaRepository.findAll()
                .stream()
                .map(IncidentPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Page<Incident> findAll(Pageable pageable) {
        return incidentJpaRepository.findAll(pageable)
                .map(IncidentPersistenceMapper::toDomain);
    }

    @Override
    public Incident save(Incident incident) {
        return IncidentPersistenceMapper.toDomain(
                incidentJpaRepository.save(
                        IncidentPersistenceMapper.toEntity(incident)
                )
        );
    }
}