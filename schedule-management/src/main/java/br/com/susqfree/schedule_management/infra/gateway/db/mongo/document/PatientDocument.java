package br.com.susqfree.schedule_management.infra.gateway.db.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class PatientDocument {

    private UUID id;
    private String name;
    private String cpf;
    private String susNumber;

}
