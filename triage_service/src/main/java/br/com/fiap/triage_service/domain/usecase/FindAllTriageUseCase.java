package br.com.fiap.triage_service.domain.usecase;

import br.com.fiap.triage_service.domain.gateway.TriageGateway;
import br.com.fiap.triage_service.domain.mapper.TriageMapper;
import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.domain.output.TriageOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindAllTriageUseCase {

    private final TriageGateway triageGateway;

    public Page<TriageOutput> execute(Pageable pageable, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        if ("desc".equalsIgnoreCase(sortDirection)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),sort);

        Page<Triage> triages =  triageGateway.findAll(sortedPageable);

        List<TriageOutput> triageOutputs = triages.stream()
                .map(TriageMapper::toOutput)
                .collect(Collectors.toList());

        return new PageImpl<>(triageOutputs, sortedPageable, triages.getTotalElements());
    }

}
