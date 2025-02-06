package br.com.susqfree.patient_management.api.controller;

import br.com.susqfree.patient_management.domain.output.PatientOutput;
import br.com.susqfree.patient_management.domain.usecase.CreatePatientUseCase;
import br.com.susqfree.patient_management.domain.input.PatientInput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final CreatePatientUseCase createPatientUseCase;

    @PostMapping
    public ResponseEntity<PatientOutput> create(@RequestBody PatientInput input) {
        var output = createPatientUseCase.execute(input);

        return ResponseEntity.ok(output);
    }

}
