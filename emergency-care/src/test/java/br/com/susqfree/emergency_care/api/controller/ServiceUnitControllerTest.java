package br.com.susqfree.emergency_care.api.controller;

import br.com.susqfree.emergency_care.api.dto.ServiceUnitInput;
import br.com.susqfree.emergency_care.api.dto.ServiceUnitOutput;
import br.com.susqfree.emergency_care.api.mapper.ServiceUnitDtoMapper;
import br.com.susqfree.emergency_care.config.exception.GlobalExceptionHandler;
import br.com.susqfree.emergency_care.domain.model.ServiceUnit;
import br.com.susqfree.emergency_care.domain.usecase.CreateServiceUnitUseCase;
import br.com.susqfree.emergency_care.domain.usecase.FindServiceUnitByIdUseCase;
import br.com.susqfree.emergency_care.domain.usecase.ListAllServiceUnitsUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ServiceUnitControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateServiceUnitUseCase createServiceUnitUseCase;

    @Mock
    private FindServiceUnitByIdUseCase findServiceUnitByIdUseCase;

    @Mock
    private ListAllServiceUnitsUseCase listAllServiceUnitsUseCase;

    @Mock
    private ServiceUnitDtoMapper mapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        ServiceUnitController controller = new ServiceUnitController(
                createServiceUnitUseCase,
                findServiceUnitByIdUseCase,
                listAllServiceUnitsUseCase,
                mapper
        );

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private ServiceUnitInput createServiceUnitInput() {
        ServiceUnitInput input = new ServiceUnitInput();
        input.setServiceType("Emergency Unit");
        input.setCapacity(50);
        input.setUnitId(10L);
        return input;
    }

    private ServiceUnit createServiceUnit() {
        return new ServiceUnit(1L, "Emergency Unit", 50, 10L);
    }

    private ServiceUnitOutput createServiceUnitOutput(ServiceUnit serviceUnit) {
        return new ServiceUnitOutput(
                serviceUnit.getId(),
                serviceUnit.getServiceType(),
                serviceUnit.getCapacity(),
                serviceUnit.getUnitId()
        );
    }

    @Test
    @DisplayName("Deve criar uma nova unidade de serviço com sucesso")
    void shouldCreateServiceUnit() throws Exception {
        ServiceUnitInput input = createServiceUnitInput();
        ServiceUnit serviceUnit = createServiceUnit();
        ServiceUnitOutput output = createServiceUnitOutput(serviceUnit);

        when(mapper.toDomain(any(ServiceUnitInput.class))).thenReturn(serviceUnit);
        when(createServiceUnitUseCase.execute(any(ServiceUnit.class))).thenReturn(serviceUnit);
        when(mapper.toOutput(any(ServiceUnit.class))).thenReturn(output);

        mockMvc.perform(post("/service-units")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(output.getId()))
                .andExpect(jsonPath("$.serviceType").value(output.getServiceType()))
                .andExpect(jsonPath("$.capacity").value(output.getCapacity()))
                .andExpect(jsonPath("$.unitId").value(output.getUnitId()));

        verify(createServiceUnitUseCase, times(1)).execute(any(ServiceUnit.class));
    }

    @Test
    @DisplayName("Deve buscar uma unidade de serviço por ID com sucesso")
    void shouldFindServiceUnitById() throws Exception {
        ServiceUnit serviceUnit = createServiceUnit();
        ServiceUnitOutput output = createServiceUnitOutput(serviceUnit);

        when(findServiceUnitByIdUseCase.execute(eq(1L))).thenReturn(Optional.of(serviceUnit));
        when(mapper.toOutput(serviceUnit)).thenReturn(output);

        mockMvc.perform(get("/service-units/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(output.getId()))
                .andExpect(jsonPath("$.serviceType").value(output.getServiceType()))
                .andExpect(jsonPath("$.capacity").value(output.getCapacity()))
                .andExpect(jsonPath("$.unitId").value(output.getUnitId()));

        verify(findServiceUnitByIdUseCase, times(1)).execute(eq(1L));
    }

    @Test
    @DisplayName("Deve retornar 404 quando unidade de serviço não for encontrada")
    void shouldReturnNotFoundWhenServiceUnitDoesNotExist() throws Exception {
        when(findServiceUnitByIdUseCase.execute(eq(1L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/service-units/{id}", 1L))
                .andExpect(status().isNotFound());

        verify(findServiceUnitByIdUseCase, times(1)).execute(eq(1L));
    }

    @Test
    @DisplayName("Deve listar todas as unidades de serviço")
    void shouldListAllServiceUnits() throws Exception {
        ServiceUnit serviceUnit1 = new ServiceUnit(1L, "Emergency Unit", 50, 10L);
        ServiceUnit serviceUnit2 = new ServiceUnit(2L, "General Consultation", 30, 11L);

        ServiceUnitOutput output1 = new ServiceUnitOutput(1L, "Emergency Unit", 50, 10L);
        ServiceUnitOutput output2 = new ServiceUnitOutput(2L, "General Consultation", 30, 11L);

        when(listAllServiceUnitsUseCase.execute()).thenReturn(List.of(serviceUnit1, serviceUnit2));
        when(mapper.toOutput(serviceUnit1)).thenReturn(output1);
        when(mapper.toOutput(serviceUnit2)).thenReturn(output2);

        mockMvc.perform(get("/service-units"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(output1.getId()))
                .andExpect(jsonPath("$[0].serviceType").value(output1.getServiceType()))
                .andExpect(jsonPath("$[1].id").value(output2.getId()))
                .andExpect(jsonPath("$[1].serviceType").value(output2.getServiceType()));

        verify(listAllServiceUnitsUseCase, times(1)).execute();
    }
}
