package br.com.susqfree.schedule_management.api.controller;

import br.com.susqfree.schedule_management.domain.input.ConfirmAppointmentInput;
import br.com.susqfree.schedule_management.domain.input.ScheduleAppointmentInput;
import br.com.susqfree.schedule_management.domain.model.Status;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import br.com.susqfree.schedule_management.domain.usecase.*;
import br.com.susqfree.schedule_management.infra.exception.GlobalExceptionHandler;
import br.com.susqfree.schedule_management.utils.AppointmentHelper;
import br.com.susqfree.schedule_management.utils.PatientHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static br.com.susqfree.schedule_management.utils.JsonHelper.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ScheduleAppointmentUseCase scheduleAppointmentUseCase;
    @Mock
    private CancelAppointmentUseCase cancelAppointmentUseCase;
    @Mock
    private ConfirmAppointmentUseCase confirmAppointmentUsecase;
    @Mock
    private CompleteAppointmentUseCase completeAppointmentUsecase;
    @Mock
    private FindAppointmentsByPatientUseCase findAppointmentsByPatientUseCase;
    @Mock
    private FindAppointmentsAvailableByHealthUnitAndSpecialtyUseCase findAppointmentsAvailableByHealthUnitAndSpecialtyUseCase;
    @Mock
    private FindAppointmentsByHealthUnitUseCase findAppointmentsByHealthUnitUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        AppointmentController appointmentController = new AppointmentController(
                scheduleAppointmentUseCase, cancelAppointmentUseCase, confirmAppointmentUsecase, completeAppointmentUsecase,
                findAppointmentsByPatientUseCase, findAppointmentsAvailableByHealthUnitAndSpecialtyUseCase, findAppointmentsByHealthUnitUseCase
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController)
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
    public void shouldScheduleAppointments() throws Exception {
        // Arrange
        UUID appointmentId = UUID.randomUUID();
        AppointmentOutput appointmentOutput = AppointmentHelper.createAppointmentOutput(appointmentId);
        appointmentOutput.setStatus(Status.SCHEDULED);
        appointmentOutput.setPatient(PatientHelper.createPatientOutput());

        var input = ScheduleAppointmentInput.builder()
                .patientId(UUID.randomUUID())
                .appointmentId(appointmentId)
                .build();

        when(scheduleAppointmentUseCase.execute(any(ScheduleAppointmentInput.class))).thenReturn(appointmentOutput);

        // Act
        mockMvc.perform(post("/api/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(appointmentOutput.getStatus().toString()))
                .andExpect(jsonPath("$.patient").isNotEmpty());

        // Assert
        verify(scheduleAppointmentUseCase, times(1)).execute(any(ScheduleAppointmentInput.class));
    }

    @Test
    public void shouldCancelAppointments() throws Exception {
        // Arrange
        UUID appointmentId = UUID.randomUUID();
        AppointmentOutput appointmentOutput = AppointmentHelper.createAppointmentOutput(appointmentId);
        appointmentOutput.setStatus(Status.AVAILABLE);
        appointmentOutput.setPatient(null);

        when(cancelAppointmentUseCase.execute(any(UUID.class))).thenReturn(appointmentOutput);

        // Act
        mockMvc.perform(delete("/api/appointments/{appointmentId}", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(appointmentOutput.getStatus().toString()))
                .andExpect(jsonPath("$.patient").isEmpty());

        // Assert
        verify(cancelAppointmentUseCase, times(1)).execute(any(UUID.class));
    }

    @Test
    public void shouldConfirmAppointments() throws Exception {
        // Arrange
        UUID appointmentId = UUID.randomUUID();
        AppointmentOutput appointmentOutput = AppointmentHelper.createAppointmentOutput(appointmentId);
        appointmentOutput.setStatus(Status.CONFIRMED);

        var input = ConfirmAppointmentInput.builder()
                .appointmentId(appointmentId)
                .confirmed(true)
                .build();

        when(confirmAppointmentUsecase.execute(any(ConfirmAppointmentInput.class))).thenReturn(appointmentOutput);

        // Act
        mockMvc.perform(put("/api/appointments/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(appointmentOutput.getStatus().toString()));

        // Assert
        verify(confirmAppointmentUsecase, times(1)).execute(any(ConfirmAppointmentInput.class));
    }

    @Test
    public void shouldCompleteAppointments() throws Exception {
        // Arrange
        UUID appointmentId = UUID.randomUUID();
        AppointmentOutput appointmentOutput = AppointmentHelper.createAppointmentOutput(appointmentId);
        appointmentOutput.setStatus(Status.COMPLETED);

        when(completeAppointmentUsecase.execute(any(UUID.class))).thenReturn(appointmentOutput);

        // Act
        mockMvc.perform(put("/api/appointments/{appointmentId}/complete", appointmentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(appointmentOutput.getStatus().toString()));

        // Assert
        verify(completeAppointmentUsecase, times(1)).execute(any(UUID.class));
    }

    @Test
    public void shouldFindAppointmentsByPatient() throws Exception {
        // Arrange
        AppointmentOutput appointmentOutput1 = AppointmentHelper.createAppointmentOutput(UUID.randomUUID());
        AppointmentOutput appointmentOutput2 = AppointmentHelper.createAppointmentOutput(UUID.randomUUID());

        UUID patientId = UUID.randomUUID();
        LocalDateTime startDateTime = LocalDateTime.of(2025, 3, 1, 8, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2025, 3, 10, 22, 0);

        when(findAppointmentsByPatientUseCase.execute(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(appointmentOutput1, appointmentOutput2));

        // Act
        mockMvc.perform(get("/api/appointments/patient/{patientId}", patientId)
                        .queryParam("startDateTime", startDateTime.toString())
                        .queryParam("endDateTime", endDateTime.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        // Assert
        verify(findAppointmentsByPatientUseCase, times(1)).execute(any(UUID.class), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    public void shouldFindAppointmentsAvailableByHealthUnitAndSpecialty() throws Exception {
        // Arrange
        AppointmentOutput appointmentOutput1 = AppointmentHelper.createAppointmentOutput(UUID.randomUUID());
        AppointmentOutput appointmentOutput2 = AppointmentHelper.createAppointmentOutput(UUID.randomUUID());
        Page<AppointmentOutput> page = new PageImpl<>(List.of(appointmentOutput1, appointmentOutput2), PageRequest.of(0,10), 2);

        when(findAppointmentsAvailableByHealthUnitAndSpecialtyUseCase.execute(anyLong(), anyLong(), any(Pageable.class)))
                .thenReturn(page);

        long healthUnitId = 1L;
        long specialtyId = 1L;

        // Act
        mockMvc.perform(get("/api/appointments/available")
                        .queryParam("healthUnitId", String.valueOf(healthUnitId))
                        .queryParam("specialtyId", String.valueOf(specialtyId))
                        .queryParam("page", "0")
                        .queryParam("size", "10")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));

        // Assert
        verify(findAppointmentsAvailableByHealthUnitAndSpecialtyUseCase, times(1))
                .execute(anyLong(), anyLong(), any(Pageable.class));
    }

    @Test
    public void shouldFindAppointmentsByHealthUnit() throws Exception {
        // Arrange
        AppointmentOutput appointmentOutput1 = AppointmentHelper.createAppointmentOutput(UUID.randomUUID());
        AppointmentOutput appointmentOutput2 = AppointmentHelper.createAppointmentOutput(UUID.randomUUID());
        Page<AppointmentOutput> page = new PageImpl<>(List.of(appointmentOutput1, appointmentOutput2), PageRequest.of(0,10), 2);

        long healthUnitId = 1L;
        LocalDateTime startDateTime = LocalDateTime.of(2025, 3, 1, 8, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2025, 3, 10, 22, 0);

        when(findAppointmentsByHealthUnitUseCase.execute(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(page);

        // Act
        mockMvc.perform(get("/api/appointments/health-unit/{healthUnitId}", healthUnitId)
                        .queryParam("startDateTime", startDateTime.toString())
                        .queryParam("endDateTime", endDateTime.toString())
                        .queryParam("page", "0")
                        .queryParam("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)));

        // Assert
        verify(findAppointmentsByHealthUnitUseCase, times(1))
                .execute(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class));
    }

}