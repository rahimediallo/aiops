package com.rahim.aiops.domain.port;

import com.rahim.aiops.domain.model.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IncidentRepositoryPort {
    List<Incident> findAll();
    Incident save(Incident incident);
    Page<Incident> findAll(Pageable pageable);

}