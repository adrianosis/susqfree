package br.com.susqfree.emergency_care.api.controller;

import br.com.susqfree.emergency_care.api.dto.AttendanceInput;
import br.com.susqfree.emergency_care.api.dto.AttendanceOutput;
import br.com.susqfree.emergency_care.api.mapper.AttendanceDtoMapper;
import br.com.susqfree.emergency_care.config.exception.GlobalExceptionHandler;
import br.com.susqfree.emergency_care.domain.enums.AttendanceStatus;
import br.com.susqfree.emergency_care.domain.model.Attendance;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import br.com.susqfree.emergency_care.domain.model.TriageInput;
import br.com.susqfree.emergency_care.domain.usecase.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AttendanceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateAttendanceUseCase createAttendanceUseCase;
    @Mock
    private CallNextAttendanceUseCase callNextAttendanceUseCase;
    @Mock
    private CompleteAttendanceUseCase completeAttendanceUseCase;
    @Mock
    private CancelAttendanceUseCase cancelAttendanceUseCase;
    @Mock
    private AttendanceDtoMapper mapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        AttendanceController attendanceController = new AttendanceController(
                createAttendanceUseCase,
                callNextAttendanceUseCase,
                completeAttendanceUseCase,
                cancelAttendanceUseCase,
                mapper
        );

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(attendanceController)
                .setMessageConverters(jsonConverter)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private AttendanceInput createAttendanceInput(UUID patientId) {
        AttendanceInput input = new AttendanceInput();
        input.setPatientId(patientId);
        input.setServiceUnitId(2L);
        input.setPriorityLevel("EMERGENCY");
        return input;
    }

    private Attendance createAttendance(UUID patientId) {
        ServiceUnit serviceUnit = new ServiceUnit(2L, "Emergency Unit", 100, 10L);
        return new Attendance(1L, patientId, serviceUnit, AttendanceStatus.AWAITING_ATTENDANCE, "R00001");
    }

    private AttendanceOutput createAttendanceOutput(Attendance attendance) {
        return new AttendanceOutput(
                attendance.getId(),
                attendance.getPatientId(),
                attendance.getServiceUnit().getServiceType(),
                attendance.getStatus().name(),
                attendance.getTicket()
        );
    }

    @Test
    @DisplayName("Deve criar um novo atendimento com sucesso")
    void shouldCreateAttendance() throws Exception {
        UUID patientId = UUID.randomUUID();
        Long serviceUnitId = 2L;

        // Criando um TriageInput válido
        TriageInput input = new TriageInput(
                patientId,
                "Dor intensa no peito",
                "MENOS_DE_UM_DIA",
                39.5,
                "NORMAL",
                "TAQUICARDIA",
                "NORMAL",
                "NORMAL",
                List.of("DOR_PEITO", "FALTA_AR"),
                "INTENSA",
                "NAO",
                List.of("HIPERTENSAO"),
                List.of(),
                "NAO",
                List.of()
        );

        Attendance attendance = createAttendance(patientId);
        AttendanceOutput output = createAttendanceOutput(attendance);

        when(createAttendanceUseCase.execute(eq(patientId), eq(serviceUnitId), any(TriageInput.class)))
                .thenReturn(attendance);
        when(mapper.toOutput(attendance)).thenReturn(output);

        mockMvc.perform(post("/attendances/{serviceUnitId}", serviceUnitId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(output.getId()))
                .andExpect(jsonPath("$.serviceType").value(output.getServiceType()))
                .andExpect(jsonPath("$.status").value(output.getStatus()))
                .andExpect(jsonPath("$.ticket").value(output.getTicket()));

        verify(createAttendanceUseCase, times(1)).execute(eq(patientId), eq(serviceUnitId), any(TriageInput.class));
    }


    @Test
    @DisplayName("Deve chamar o próximo atendimento na fila")
    void shouldCallNextAttendance() throws Exception {
        UUID patientId = UUID.randomUUID();
        Attendance attendance = createAttendance(patientId);
        AttendanceOutput output = createAttendanceOutput(attendance);

        when(callNextAttendanceUseCase.execute(eq(2L))).thenReturn(Optional.of(attendance));
        when(mapper.toOutput(attendance)).thenReturn(output);

        mockMvc.perform(post("/attendances/call-next/{serviceUnitId}", 2L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(output.getId()))
                .andExpect(jsonPath("$.serviceType").value(output.getServiceType()))
                .andExpect(jsonPath("$.status").value(output.getStatus()))
                .andExpect(jsonPath("$.ticket").value(output.getTicket()));

        verify(callNextAttendanceUseCase, times(1)).execute(eq(2L));
    }

    @Test
    @DisplayName("Deve retornar status 204 quando não houver próximo atendimento")
    void shouldReturnNoContentWhenNoNextAttendance() throws Exception {
        when(callNextAttendanceUseCase.execute(eq(2L))).thenReturn(Optional.empty());

        mockMvc.perform(post("/attendances/call-next/{serviceUnitId}", 2L))
                .andExpect(status().isNoContent());

        verify(callNextAttendanceUseCase, times(1)).execute(eq(2L));
    }

    @Test
    @DisplayName("Deve completar um atendimento com sucesso")
    void shouldCompleteAttendance() throws Exception {
        doNothing().when(completeAttendanceUseCase).execute(eq(1L));

        mockMvc.perform(delete("/attendances/{id}/complete", 1L))
                .andExpect(status().isNoContent());

        verify(completeAttendanceUseCase, times(1)).execute(eq(1L));
    }

    @Test
    @DisplayName("Deve cancelar um atendimento com sucesso")
    void shouldCancelAttendance() throws Exception {
        doNothing().when(cancelAttendanceUseCase).execute(eq(1L));

        mockMvc.perform(delete("/attendances/{id}/cancel", 1L))
                .andExpect(status().isNoContent());

        verify(cancelAttendanceUseCase, times(1)).execute(eq(1L));
    }

}
