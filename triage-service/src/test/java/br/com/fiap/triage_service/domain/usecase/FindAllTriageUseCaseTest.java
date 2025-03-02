package br.com.fiap.triage_service.domain.usecase;

import br.com.fiap.triage_service.domain.gateway.TriageGateway;
import br.com.fiap.triage_service.domain.model.Triage;
import br.com.fiap.triage_service.domain.output.TriageOutput;
import br.com.fiap.triage_service.helper.TriageHelper;
import br.com.fiap.triage_service.infra.gateway.jpa.entity.PriorityCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindAllTriageUseCaseTest {

    private FindAllTriageUseCase findAllTriageUseCase;

    @Mock
    private TriageGateway triageGateway;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        findAllTriageUseCase = new FindAllTriageUseCase(triageGateway);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void shouldReturnPagedTriagesAscOrderSuccessfully() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
        List<Triage> triageList = List.of(
                TriageHelper.createTriage(1, PriorityCode.R),
                TriageHelper.createTriage(2, PriorityCode.R));
        Page<Triage> triagePage = new PageImpl<>(triageList, pageable, triageList.size());

        when(triageGateway.findAll(any(Pageable.class))).thenReturn(triagePage);

        Page<TriageOutput> result = findAllTriageUseCase.execute(pageable, "id", "asc");

        System.out.println(result);
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .extracting(TriageOutput::getId)
                .containsExactly(1, 2);

        verify(triageGateway, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void shouldReturnPagedTriagesDescOrderSuccessfully() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        List<Triage> triageList = List.of(
                TriageHelper.createTriage(2, PriorityCode.R),
                TriageHelper.createTriage(1, PriorityCode.R));
        Page<Triage> triagePage = new PageImpl<>(triageList, pageable, triageList.size());

        when(triageGateway.findAll(any(Pageable.class))).thenReturn(triagePage);

        Page<TriageOutput> result = findAllTriageUseCase.execute(pageable, "id", "desc");

        System.out.println(result);
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .extracting(TriageOutput::getId)
                .containsExactly( 2,1);

        verify(triageGateway, times(1)).findAll(any(Pageable.class));
    }
}