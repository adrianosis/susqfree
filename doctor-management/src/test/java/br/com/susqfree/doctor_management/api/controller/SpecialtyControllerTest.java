package br.com.susqfree.doctor_management.api.controller;

import br.com.susqfree.doctor_management.api.dto.SpecialtyInput;
import br.com.susqfree.doctor_management.api.dto.SpecialtyOutput;
import br.com.susqfree.doctor_management.domain.model.Specialty;
import br.com.susqfree.doctor_management.domain.usecase.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpecialtyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CreateSpecialtyUseCase createSpecialtyUseCase;

    @Autowired
    private UpdateSpecialtyUseCase updateSpecialtyUseCase;

    @Autowired
    private DeleteSpecialtyUseCase deleteSpecialtyUseCase;

    @Autowired
    private FindAllSpecialtiesUseCase findAllSpecialtiesUseCase;

    private Specialty specialty;
    private SpecialtyInput specialtyInput;
    private SpecialtyOutput specialtyOutput;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        specialty = new Specialty(1L, "Cardiologia", "Especialidade dedicada ao estudo do coração.");
        specialtyInput = new SpecialtyInput("Cardiologia", "Especialidade dedicada ao estudo do coração.");
        specialtyOutput = new SpecialtyOutput(1L, "Cardiologia", "Especialidade dedicada ao estudo do coração.");
    }

    @Test
    void shouldCreateSpecialtySuccessfully() throws Exception {
        when(createSpecialtyUseCase.execute(any(Specialty.class))).thenReturn(specialty);

        mockMvc.perform(post("/specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(specialtyInput)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(specialtyOutput.id()))
                .andExpect(jsonPath("$.name").value(specialtyOutput.name()))
                .andExpect(jsonPath("$.description").value(specialtyOutput.description()));
    }

    @Test
    void shouldUpdateSpecialtySuccessfully() throws Exception {
        when(updateSpecialtyUseCase.execute(any(Specialty.class))).thenReturn(specialty);

        mockMvc.perform(put("/specialties/{id}", specialty.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(specialtyInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(specialtyOutput.id()))
                .andExpect(jsonPath("$.name").value(specialtyOutput.name()));
    }

    @Test
    void shouldDeleteSpecialtySuccessfully() throws Exception {
        mockMvc.perform(delete("/specialties/{id}", specialty.id()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldFindAllSpecialtiesSuccessfully() throws Exception {
        when(findAllSpecialtiesUseCase.execute()).thenReturn(List.of(specialty));

        mockMvc.perform(get("/specialties")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(specialtyOutput.id()))
                .andExpect(jsonPath("$[0].name").value(specialtyOutput.name()));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        CreateSpecialtyUseCase createSpecialtyUseCase() {
            return Mockito.mock(CreateSpecialtyUseCase.class);
        }

        @Bean
        UpdateSpecialtyUseCase updateSpecialtyUseCase() {
            return Mockito.mock(UpdateSpecialtyUseCase.class);
        }

        @Bean
        DeleteSpecialtyUseCase deleteSpecialtyUseCase() {
            return Mockito.mock(DeleteSpecialtyUseCase.class);
        }

        @Bean
        FindAllSpecialtiesUseCase findAllSpecialtiesUseCase() {
            return Mockito.mock(FindAllSpecialtiesUseCase.class);
        }
    }
}
