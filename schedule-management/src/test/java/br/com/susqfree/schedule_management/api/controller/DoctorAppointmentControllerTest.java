package br.com.susqfree.schedule_management.api.controller;

import br.com.susqfree.schedule_management.domain.input.CancelDoctorAppointmentsInput;
import br.com.susqfree.schedule_management.domain.input.CreateAppointmentInput;
import br.com.susqfree.schedule_management.domain.model.Status;
import br.com.susqfree.schedule_management.domain.output.AppointmentOutput;
import br.com.susqfree.schedule_management.domain.usecase.CancelDoctorAppointmentsUseCase;
import br.com.susqfree.schedule_management.domain.usecase.CreateDoctorAppointmentsUseCase;
import br.com.susqfree.schedule_management.domain.usecase.FindAppointmentsByDoctorUseCase;
import br.com.susqfree.schedule_management.infra.exception.GlobalExceptionHandler;
import br.com.susqfree.schedule_management.utils.AppointmentHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static br.com.susqfree.schedule_management.utils.JsonHelper.asJsonString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DoctorAppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateDoctorAppointmentsUseCase createDoctorAppointmentsUseCase;
    @Mock
    private CancelDoctorAppointmentsUseCase cancelDoctorAppointmentsUseCase;
    @Mock
    private FindAppointmentsByDoctorUseCase findAppointmentsByDoctorUseCase;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        DoctorAppointmentController doctorAppointmentController = new DoctorAppointmentController(
                createDoctorAppointmentsUseCase, cancelDoctorAppointmentsUseCase, findAppointmentsByDoctorUseCase);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(doctorAppointmentController)
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
    public void shouldCreateDoctorAppointments() throws Exception {
        // Arrange
        UUID appointmentId = UUID.randomUUID();
        AppointmentOutput appointmentOutput1 = AppointmentHelper.createAppointmentOutput(appointmentId);
        AppointmentOutput appointmentOutput2 = AppointmentHelper.createAppointmentOutput(appointmentId);

        var input = CreateAppointmentInput.builder()
                .doctorId(1L)
                .healthUnitId(1L)
                .specialtyId(1L)
                .startDateTime(LocalDateTime.of(2025, 2, 24, 8, 0))
                .endDateTime(LocalDateTime.of(2025, 2, 24, 12, 0))
                .build();

        when(createDoctorAppointmentsUseCase.execute(anyList())).thenReturn(List.of(appointmentOutput1, appointmentOutput2));

        // Act
        mockMvc.perform(post("/api/appointments/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(List.of(input))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        // Assert
        verify(createDoctorAppointmentsUseCase, times(1)).execute(anyList());
    }

    @Test
    public void shouldCancelDoctorAppointments() throws Exception {
        // Arrange
        long doctorId = 1L;
        String justification = "justification";
        AppointmentOutput appointmentOutput1 = AppointmentHelper.createAppointmentOutput(UUID.randomUUID());
        appointmentOutput1.setStatus(Status.CANCELLED);
        appointmentOutput1.setJustification(justification);
        AppointmentOutput appointmentOutput2 = AppointmentHelper.createAppointmentOutput(UUID.randomUUID());
        appointmentOutput2.setStatus(Status.CANCELLED);
        appointmentOutput2.setJustification(justification);

        var input = CancelDoctorAppointmentsInput.builder()
                .doctorId(doctorId)
                .startDateTime(LocalDateTime.of(2025, 3, 1, 8, 0))
                .endDateTime(LocalDateTime.of(2025, 3, 1, 18, 0))
                .justification(justification)
                .build();

        when(cancelDoctorAppointmentsUseCase.execute(any(CancelDoctorAppointmentsInput.class))).thenReturn(List.of(appointmentOutput1, appointmentOutput2));

        // Act
        mockMvc.perform(delete("/api/appointments/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].status").value(appointmentOutput1.getStatus().toString()))
                .andExpect(jsonPath("$[0].justification").value(appointmentOutput1.getJustification()));

        // Assert
        verify(cancelDoctorAppointmentsUseCase, times(1)).execute(any(CancelDoctorAppointmentsInput.class));
    }

    @Test
    public void shouldFindAppointmentsByDoctorId() throws Exception {
        // Arrange
        AppointmentOutput appointmentOutput1 = AppointmentHelper.createAppointmentOutput(UUID.randomUUID());
        AppointmentOutput appointmentOutput2 = AppointmentHelper.createAppointmentOutput(UUID.randomUUID());

        long doctorId = 1L;
        LocalDateTime startDateTime = LocalDateTime.of(2025, 3, 1, 8, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2025, 3, 10, 22, 0);

        when(findAppointmentsByDoctorUseCase.execute(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(appointmentOutput1, appointmentOutput2));

        // Act
        mockMvc.perform(get("/api/appointments/doctor/{doctorId}", doctorId)
                        .queryParam("startDateTime", startDateTime.toString())
                        .queryParam("endDateTime", endDateTime.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        // Assert
        verify(findAppointmentsByDoctorUseCase, times(1)).execute(anyLong(), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    public void shouldReturnBadRequestWhenNullValuesWhenCreateAppointments() throws Exception {
        // Arrange
        var input = CreateAppointmentInput.builder()
                .doctorId(1L)
                .healthUnitId(1L)
                .specialtyId(1L)
                .build();

        // Act
        ResultActions result =      mockMvc.perform(post("/api/appointments/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(List.of(input))))
                .andDo(print());

        // Assert
        result.andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value("400 BAD_REQUEST \"Validation failure\""));

    }

    @Test
    public void shouldReturnBadRequestWhenNullValuesWhenCancelAppointments() throws Exception {
        // Arrange
        var input = CancelDoctorAppointmentsInput.builder()
                .doctorId(1L)
                .startDateTime(LocalDateTime.of(2025, 3, 1, 8, 0))
                .endDateTime(LocalDateTime.of(2025, 3, 1, 18, 0))
                .build();

        // Act
        ResultActions result =      mockMvc.perform(delete("/api/appointments/doctor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(input)))
                .andDo(print());

        // Assert
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.fieldErrors.justification").value("must not be blank"));
    }

}