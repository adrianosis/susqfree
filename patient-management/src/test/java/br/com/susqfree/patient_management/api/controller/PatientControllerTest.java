package br.com.susqfree.patient_management.api.controller;

import br.com.susqfree.patient_management.domain.input.CreatePatientInput;
import br.com.susqfree.patient_management.domain.input.UpdatePatientInput;
import br.com.susqfree.patient_management.domain.output.PatientOutput;
import br.com.susqfree.patient_management.domain.usecase.*;
import br.com.susqfree.patient_management.infra.exceptions.GlobalExceptionHandler;
import br.com.susqfree.patient_management.infra.exceptions.PatientManagementException;
import br.com.susqfree.patient_management.utils.PatientHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static br.com.susqfree.patient_management.utils.JsonHelper.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PatientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreatePatientUseCase createPatientUseCase;
    @Mock
    private UpdatePatientUseCase updatePatientUseCase;
    @Mock
    private FindPatientByIdUseCase findPatientByIdUseCase;
    @Mock
    private FindPatientByCpfUseCase findPatientByCpfUseCase;
    @Mock
    private FindAllPatientsUseCase findAllPatientsUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        PatientController patientController = new PatientController(createPatientUseCase, updatePatientUseCase,
                findPatientByIdUseCase, findPatientByCpfUseCase, findAllPatientsUseCase);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(patientController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();

    }

    @Test
    public void shouldCreatePatient() throws Exception {
        // Arrange
        PatientOutput patientOutput = PatientHelper.createPatientOutput(UUID.randomUUID());

        CreatePatientInput input = PatientHelper.createPatientInput();

        when(createPatientUseCase.execute(any(CreatePatientInput.class))).thenReturn(patientOutput);

        // Act
        mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(input))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientOutput.getId().toString()))
                .andExpect(jsonPath("$.name").value(patientOutput.getName()))
                .andExpect(jsonPath("$.gender").value(patientOutput.getGender()))
                .andExpect(jsonPath("$.cpf").value(patientOutput.getCpf()))
                .andExpect(jsonPath("$.susNumber").value(patientOutput.getSusNumber()))
                .andExpect(jsonPath("$.phoneNumber").value(patientOutput.getPhoneNumber()))
                .andExpect(jsonPath("$.mail").value(patientOutput.getMail()))
                .andExpect(jsonPath("$.street").value(patientOutput.getStreet()))
                .andExpect(jsonPath("$.number").value(patientOutput.getNumber()))
                .andExpect(jsonPath("$.district").value(patientOutput.getDistrict()))
                .andExpect(jsonPath("$.city").value(patientOutput.getCity()))
                .andExpect(jsonPath("$.state").value(patientOutput.getState()))
                .andExpect(jsonPath("$.postalCode").value(patientOutput.getPostalCode()))
                .andExpect(jsonPath("$.latitude").value(patientOutput.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(patientOutput.getLongitude()));

        // Assert
        verify(createPatientUseCase, times(1)).execute(any(CreatePatientInput.class));
    }

    @Test
    public void shouldUpdatePatient() throws Exception {
        // Arrange
        UUID patientId = UUID.randomUUID();
        PatientOutput patientOutput = PatientHelper.createPatientOutput(patientId);

        when(updatePatientUseCase.execute(any(UUID.class), any(UpdatePatientInput.class))).thenReturn(patientOutput);

        var input = UpdatePatientInput.builder()
                .name("Julia Silva")
                .gender("O")
                .phoneNumber("11999993333")
                .mail("julia.silva@test.com")
                .street("Rua Capitão")
                .number("1450")
                .district("Limões")
                .city("Santa Catarina")
                .state("SC")
                .postalCode("22224444")
                .latitude(-23.562643)
                .longitude(-46.654888)
                .build();

        // Act
        mockMvc.perform(put("/api/patient/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(input))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientOutput.getId().toString()))
                .andExpect(jsonPath("$.name").value(patientOutput.getName()))
                .andExpect(jsonPath("$.gender").value(patientOutput.getGender()))
                .andExpect(jsonPath("$.phoneNumber").value(patientOutput.getPhoneNumber()))
                .andExpect(jsonPath("$.mail").value(patientOutput.getMail()))
                .andExpect(jsonPath("$.street").value(patientOutput.getStreet()))
                .andExpect(jsonPath("$.number").value(patientOutput.getNumber()))
                .andExpect(jsonPath("$.district").value(patientOutput.getDistrict()))
                .andExpect(jsonPath("$.city").value(patientOutput.getCity()))
                .andExpect(jsonPath("$.state").value(patientOutput.getState()))
                .andExpect(jsonPath("$.postalCode").value(patientOutput.getPostalCode()))
                .andExpect(jsonPath("$.latitude").value(patientOutput.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(patientOutput.getLongitude()));

        // Assert
        verify(updatePatientUseCase, times(1)).execute(any(UUID.class), any(UpdatePatientInput.class));
    }

    @Test
    public void shouldFindPatientById() throws Exception {
        // Arrange
        UUID patientId = UUID.randomUUID();
        PatientOutput patientOutput = PatientHelper.createPatientOutput(patientId);

        when(findPatientByIdUseCase.execute(any(UUID.class))).thenReturn(patientOutput);

        // Act
        mockMvc.perform(get("/api/patient/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientOutput.getId().toString()))
                .andExpect(jsonPath("$.name").value(patientOutput.getName()))
                .andExpect(jsonPath("$.gender").value(patientOutput.getGender()))
                .andExpect(jsonPath("$.cpf").value(patientOutput.getCpf()))
                .andExpect(jsonPath("$.susNumber").value(patientOutput.getSusNumber()))
                .andExpect(jsonPath("$.phoneNumber").value(patientOutput.getPhoneNumber()))
                .andExpect(jsonPath("$.mail").value(patientOutput.getMail()))
                .andExpect(jsonPath("$.street").value(patientOutput.getStreet()))
                .andExpect(jsonPath("$.number").value(patientOutput.getNumber()))
                .andExpect(jsonPath("$.district").value(patientOutput.getDistrict()))
                .andExpect(jsonPath("$.city").value(patientOutput.getCity()))
                .andExpect(jsonPath("$.state").value(patientOutput.getState()))
                .andExpect(jsonPath("$.postalCode").value(patientOutput.getPostalCode()))
                .andExpect(jsonPath("$.latitude").value(patientOutput.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(patientOutput.getLongitude()));

        // Assert
        verify(findPatientByIdUseCase, times(1)).execute(any(UUID.class));
    }


    @Test
    public void shouldFindPatientByCpf() throws Exception {
        // Arrange
        PatientOutput patientOutput = PatientHelper.createPatientOutput(UUID.randomUUID());
        String cpf = patientOutput.getCpf();

        when(findPatientByCpfUseCase.execute(anyString())).thenReturn(patientOutput);

        // Act
        mockMvc.perform(get("/api/patient/cpf/{cpf}", cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(patientOutput.getId().toString()))
                .andExpect(jsonPath("$.name").value(patientOutput.getName()))
                .andExpect(jsonPath("$.gender").value(patientOutput.getGender()))
                .andExpect(jsonPath("$.cpf").value(patientOutput.getCpf()))
                .andExpect(jsonPath("$.susNumber").value(patientOutput.getSusNumber()))
                .andExpect(jsonPath("$.phoneNumber").value(patientOutput.getPhoneNumber()))
                .andExpect(jsonPath("$.mail").value(patientOutput.getMail()))
                .andExpect(jsonPath("$.street").value(patientOutput.getStreet()))
                .andExpect(jsonPath("$.number").value(patientOutput.getNumber()))
                .andExpect(jsonPath("$.district").value(patientOutput.getDistrict()))
                .andExpect(jsonPath("$.city").value(patientOutput.getCity()))
                .andExpect(jsonPath("$.state").value(patientOutput.getState()))
                .andExpect(jsonPath("$.postalCode").value(patientOutput.getPostalCode()))
                .andExpect(jsonPath("$.latitude").value(patientOutput.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(patientOutput.getLongitude()));

        // Assert
        verify(findPatientByCpfUseCase, times(1)).execute(anyString());
    }

    @Test
    public void shouldFindAllPatient() throws Exception {
        // Arrange
        PatientOutput patient1 = PatientHelper.createPatientOutput(UUID.randomUUID());
        PatientOutput patient2 = PatientHelper.createPatientOutput(UUID.randomUUID());
        Page<PatientOutput> page = new PageImpl<>(List.of(patient1, patient2), PageRequest.of(0,10), 2);

        when(findAllPatientsUseCase.execute(any(Pageable.class))).thenReturn(page);

        // Act
        mockMvc.perform(get("/api/patient")
                .queryParam("page", "0")
                .queryParam("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));

        // Assert
        verify(findAllPatientsUseCase, times(1)).execute(any(Pageable.class));
    }

    @Test
    void shouldReturnBadRequestWhenNameExceededSize() throws Exception {
        // Arrange
        var input =  CreatePatientInput.builder()
                .name(RandomString.make(61))
                .gender("F")
                .birthDate(LocalDate.of(2005, 6, 24))
                .cpf("11133355500")
                .susNumber("123412341234")
                .phoneNumber("11999995555")
                .mail("julia@test.com")
                .street("Av. Paulista")
                .number("1400")
                .district("Bela Vista")
                .city("São Paulo")
                .state("SP")
                .postalCode("12345678")
                .latitude(-23.562642)
                .longitude(-46.654887)
                .build();

        // Act
        ResultActions result = mockMvc.perform(post("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(input)))
                .andDo(print());

        // Assert
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.fieldErrors.name").value("size must be between 0 and 60"));

    }

    @Test
    public void shouldReturnBadRequest_WhenFindByCpf_WithInvalidCpf() throws Exception {
        // Arrange
        String cpf = "111";

        when(findPatientByCpfUseCase.execute(anyString())).thenThrow(new PatientManagementException("Patient not found"));

        // Act
        ResultActions result = mockMvc.perform(get("/api/patient/cpf/{cpf}", cpf)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }

}