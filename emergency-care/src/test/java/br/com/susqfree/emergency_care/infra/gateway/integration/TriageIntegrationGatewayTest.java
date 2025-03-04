package br.com.susqfree.emergency_care.infra.gateway.integration;

import br.com.susqfree.emergency_care.domain.model.TriageInput;
import br.com.susqfree.emergency_care.domain.model.TriagePriorityOutput;
import br.com.susqfree.emergency_care.infra.gateway.integration.client.TriageClient;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.TriageInputDto;
import br.com.susqfree.emergency_care.infra.gateway.integration.dto.TriagePriorityOutputDto;
import br.com.susqfree.emergency_care.infra.gateway.integration.mapper.TriageDtoMapper;
import br.com.susqfree.emergency_care.utils.TriageHelper;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TriageIntegrationGatewayTest {

    private TriageIntegrationGateway triageIntegrationGateway;

    @Mock
    private TriageClient triageClient;

    private final TriageDtoMapper mapper = new TriageDtoMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        triageIntegrationGateway = new TriageIntegrationGateway(triageClient, mapper);
    }

    @Test
    void shouldProcessTriageSuccessfully() {
        TriageInput triageInput = TriageHelper.createTriageInput();
        TriageInputDto triageInputDto = mapper.toDto(triageInput);
        TriagePriorityOutputDto triagePriorityDto = new TriagePriorityOutputDto("R", "Emergência - Atendimento imediato necessário.");

        when(triageClient.processTriage(triageInputDto)).thenReturn(triagePriorityDto);

        TriagePriorityOutput priorityOutput = triageIntegrationGateway.processTriage(triageInput);

        verify(triageClient, times(1)).processTriage(triageInputDto);

        assertThat(priorityOutput).isNotNull();
        assertThat(priorityOutput.priority()).isEqualTo(triagePriorityDto.priority());
        assertThat(priorityOutput.diagnosis()).isEqualTo(triagePriorityDto.diagnosis());
    }

    @Test
    void shouldThrowExceptionWhenTriageFails() {
        TriageInput triageInput = TriageHelper.createTriageInput();
        TriageInputDto triageInputDto = mapper.toDto(triageInput);

        when(triageClient.processTriage(triageInputDto)).thenThrow(FeignException.class);

        assertThrows(RuntimeException.class, () -> triageIntegrationGateway.processTriage(triageInput));

        verify(triageClient, times(1)).processTriage(triageInputDto);
    }
}
