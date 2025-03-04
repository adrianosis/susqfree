package br.com.susqfree.emergency_care.domain.gateway;

import br.com.susqfree.emergency_care.domain.model.TriageInput;
import br.com.susqfree.emergency_care.domain.model.TriagePriorityOutput;

public interface TriageGateway {

    TriagePriorityOutput processTriage(TriageInput triageInput);

}
