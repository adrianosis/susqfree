package br.com.fiap.triage_service.domain.gateway;


import br.com.fiap.triage_service.domain.model.Triage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TriageGateway {

    Triage create(Triage triage);
    Page<Triage> findAll(Pageable pageable);
    List<Triage> findByPatientId(Integer id);
}
