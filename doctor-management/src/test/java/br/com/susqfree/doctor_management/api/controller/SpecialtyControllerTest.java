package br.com.susqfree.doctor_management.api.controller;

import br.com.susqfree.doctor_management.api.dto.SpecialtyInput;
import br.com.susqfree.doctor_management.api.dto.SpecialtyOutput;
import br.com.susqfree.doctor_management.domain.model.Specialty;
import br.com.susqfree.doctor_management.domain.usecase.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SpecialtyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateSpecialtyUseCase createSpecialtyUseCase;
    @Mock
    private UpdateSpecialtyUseCase updateSpecialtyUseCase;
    @Mock
    private DeleteSpecialtyUseCase deleteSpecialtyUseCase;
    @Mock
    private FindSpecialtyByIdUseCase findSpecialtyByIdUseCase;
    @Mock
    private FindAllSpecialtiesUseCase findAllSpecialtiesUseCase;

    private Specialty specialty;
    private SpecialtyInput specialtyInput;
    private SpecialtyOutput specialtyOutput;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        SpecialtyController specialtyController = new SpecialtyController(createSpecialtyUseCase, updateSpecialtyUseCase,
                deleteSpecialtyUseCase, findSpecialtyByIdUseCase, findAllSpecialtiesUseCase);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(specialtyController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();

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
