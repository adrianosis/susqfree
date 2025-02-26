package br.com.susqfree.schedule_management.infra.gateway.db.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class SpecialtyDocument {

    private Long id;
    private String name;

}
