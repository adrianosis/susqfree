package br.com.susqfree.doctor_management.api.controller;

import br.com.susqfree.doctor_management.api.dto.DoctorInput;
import br.com.susqfree.doctor_management.api.dto.DoctorOutput;
import br.com.susqfree.doctor_management.domain.model.Doctor;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DoctorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateDoctorUseCase createDoctorUseCase;
    @Mock
    private UpdateDoctorUseCase updateDoctorUseCase;
    @Mock
    private DeleteDoctorUseCase deleteDoctorUseCase;
    @Mock
    private FindDoctorByIdUseCase findDoctorByIdUseCase;
    @Mock
    private FindDoctorsBySpecialtyUseCase findDoctorsBySpecialtyUseCase;

    private Doctor doctor;
    private DoctorInput doctorInput;
    private DoctorOutput doctorOutput;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        DoctorController doctorController = new DoctorController(createDoctorUseCase, updateDoctorUseCase, deleteDoctorUseCase,
                findDoctorByIdUseCase, findDoctorsBySpecialtyUseCase);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(doctorController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();

        doctor = new Doctor(1L, "Dr. Carlos Silva", "123456-SP", "(11) 91234-5678", "carlos.silva@exemplo.com", List.of());
        doctorInput = new DoctorInput("Dr. Carlos Silva", "123456-SP", "(11) 91234-5678", "carlos.silva@exemplo.com", List.of(1L, 2L));
        doctorOutput = new DoctorOutput(1L, "Dr. Carlos Silva", "123456-SP", "(11) 91234-5678", "carlos.silva@exemplo.com", List.of(1L, 2L));
    }

    @Test
    void shouldCreateDoctorSuccessfully() throws Exception {
        when(createDoctorUseCase.execute(any(Doctor.class))).thenReturn(doctor);

        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorInput)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(doctorOutput.id()))
                .andExpect(jsonPath("$.name").value(doctorOutput.name()))
                .andExpect(jsonPath("$.crm").value(doctorOutput.crm()));
    }

    @Test
    void shouldUpdateDoctorSuccessfully() throws Exception {
        when(updateDoctorUseCase.execute(any(Doctor.class))).thenReturn(doctor);

        mockMvc.perform(put("/doctors/{id}", doctor.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(doctorOutput.id()))
                .andExpect(jsonPath("$.name").value(doctorOutput.name()));
    }

    @Test
    void shouldDeleteDoctorSuccessfully() throws Exception {
        mockMvc.perform(delete("/doctors/{id}", doctor.id()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldFindDoctorByIdSuccessfully() throws Exception {
        when(findDoctorByIdUseCase.execute(doctor.id())).thenReturn(Optional.of(doctor));

        mockMvc.perform(get("/doctors/{id}", doctor.id())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(doctorOutput.id()))
                .andExpect(jsonPath("$.name").value(doctorOutput.name()))
                .andExpect(jsonPath("$.crm").value(doctorOutput.crm()));
    }

    @Test
    void shouldReturnNotFoundWhenDoctorDoesNotExist() throws Exception {
        when(findDoctorByIdUseCase.execute(doctor.id())).thenReturn(Optional.empty());

        mockMvc.perform(get("/doctors/{id}", doctor.id()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFindDoctorsBySpecialtySuccessfully() throws Exception {
        when(findDoctorsBySpecialtyUseCase.execute("Cardiology")).thenReturn(List.of(doctor));

        mockMvc.perform(get("/doctors/specialty/{name}", "Cardiology")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(doctorOutput.id()))
                .andExpect(jsonPath("$[0].name").value(doctorOutput.name()));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        CreateDoctorUseCase createDoctorUseCase() {
            return Mockito.mock(CreateDoctorUseCase.class);
        }

        @Bean
        UpdateDoctorUseCase updateDoctorUseCase() {
            return Mockito.mock(UpdateDoctorUseCase.class);
        }

        @Bean
        DeleteDoctorUseCase deleteDoctorUseCase() {
            return Mockito.mock(DeleteDoctorUseCase.class);
        }

        @Bean
        FindDoctorByIdUseCase findDoctorByIdUseCase() {
            return Mockito.mock(FindDoctorByIdUseCase.class);
        }

        @Bean
        FindDoctorsBySpecialtyUseCase findDoctorsBySpecialtyUseCase() {
            return Mockito.mock(FindDoctorsBySpecialtyUseCase.class);
        }
    }
}
