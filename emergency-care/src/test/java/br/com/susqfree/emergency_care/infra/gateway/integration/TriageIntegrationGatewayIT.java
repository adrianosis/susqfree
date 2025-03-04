package br.com.susqfree.emergency_care.infra.gateway.integration;

import br.com.susqfree.emergency_care.domain.model.TriageInput;
import br.com.susqfree.emergency_care.domain.model.TriagePriorityOutput;
import br.com.susqfree.emergency_care.utils.JsonHelper;
import br.com.susqfree.emergency_care.utils.TriageHelper;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "spring.cloud.discovery.client.simple.instances.triage-service[0].uri=http://localhost:8282"
})
@ActiveProfiles("test")
@WireMockTest(httpPort = 8282)
public class TriageIntegrationGatewayIT {

    @Autowired
    private TriageIntegrationGateway triageIntegrationGateway;

    @Test
    public void shouldProcessTriageSuccessfully() {
        TriageInput triageInput = TriageHelper.createTriageInput();
        TriagePriorityOutput expectedPriority = new TriagePriorityOutput("R", "Emergência - Atendimento imediato necessário.");

        stubFor(post(urlEqualTo("/triage/process-triage"))
                .willReturn(okJson(JsonHelper.asJsonString(expectedPriority))));

        TriagePriorityOutput priorityOutput = triageIntegrationGateway.processTriage(triageInput);

        assertThat(priorityOutput).isNotNull();
        assertThat(priorityOutput.priority()).isEqualTo(expectedPriority.priority());
        assertThat(priorityOutput.diagnosis()).isEqualTo(expectedPriority.diagnosis());
    }

    @Test
    public void shouldThrowExceptionWhenTriageFails() {
        TriageInput triageInput = TriageHelper.createTriageInput();

        stubFor(post(urlEqualTo("/triage/process-triage"))
                .willReturn(aResponse().withStatus(500)));

        var exception = assertThrows(RuntimeException.class, () -> triageIntegrationGateway.processTriage(triageInput));

        assertThat(exception.getMessage()).contains("Erro ao processar a triagem para o paciente com ID " + triageInput.patientId());
    }
}
