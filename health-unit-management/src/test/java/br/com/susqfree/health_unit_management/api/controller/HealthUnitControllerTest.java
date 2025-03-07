package br.com.susqfree.health_unit_management.api.controller;

import br.com.susqfree.health_unit_management.domain.input.HealthUnitInput;
import br.com.susqfree.health_unit_management.domain.output.HealthUnitOutput;
import br.com.susqfree.health_unit_management.domain.usecase.CreateHealthUnitUseCase;
import br.com.susqfree.health_unit_management.domain.usecase.FindAllHealthUnitsByCityAndStateUseCase;
import br.com.susqfree.health_unit_management.domain.usecase.FindHealthUnitByIdUseCase;
import br.com.susqfree.health_unit_management.domain.usecase.UpdateHealthUnitUseCase;
import br.com.susqfree.health_unit_management.infra.exception.GlobalExceptionHandler;
import br.com.susqfree.health_unit_management.utils.HealthUnitHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HealthUnitControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CreateHealthUnitUseCase createHealthUnitUseCase;
    @Mock
    private UpdateHealthUnitUseCase updateHealthUnitUseCase;
    @Mock
    private FindHealthUnitByIdUseCase findHealthUnitByIdUsecase;
    @Mock
    private FindAllHealthUnitsByCityAndStateUseCase findAllHealthUnitsByCityAndStateUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        HealthUnitController healthUnitController = new HealthUnitController(createHealthUnitUseCase,
                findHealthUnitByIdUsecase, findAllHealthUnitsByCityAndStateUseCase,updateHealthUnitUseCase);
        mockMvc = MockMvcBuilders.standaloneSetup(healthUnitController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Test
    void shouldCreateHealthUnitSuccessfully() throws Exception {
        // Arrange
        HealthUnitInput healthUnitInput = HealthUnitHelper.createHealthUnitInput();
        HealthUnitOutput healthUnitOutput = HealthUnitHelper.createHealthUnitOutput(1L);

        when(createHealthUnitUseCase.execute(any(HealthUnitInput.class))).thenReturn(healthUnitOutput);

        // Act & Assert
        mockMvc.perform(post("/health-unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(healthUnitInput)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(healthUnitOutput.getId()))
                .andExpect(jsonPath("$.name").value(healthUnitOutput.getName()))
                .andExpect(jsonPath("$.type").value(healthUnitOutput.getType().toString()))
                .andExpect(jsonPath("$.phone").value(healthUnitOutput.getPhone()))
                .andExpect(jsonPath("$.street").value(healthUnitOutput.getStreet()))
                .andExpect(jsonPath("$.number").value(healthUnitOutput.getNumber()))
                .andExpect(jsonPath("$.complement").value(healthUnitOutput.getComplement()))
                .andExpect(jsonPath("$.zipcode").value(healthUnitOutput.getZipcode()))
                .andExpect(jsonPath("$.city").value(healthUnitOutput.getCity()))
                .andExpect(jsonPath("$.state").value(healthUnitOutput.getState()))
                .andExpect(jsonPath("$.latitude").value(healthUnitOutput.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(healthUnitOutput.getLongitude()));

        verify(createHealthUnitUseCase, times(1)).execute(any(HealthUnitInput.class));
    }

    @Test
    void shouldFindHealthUnitById() throws Exception {
        // Arrange
        Long id = 1L;
        HealthUnitOutput healthUnitOutput = HealthUnitHelper.createHealthUnitOutput(1L);

        when(findHealthUnitByIdUsecase.execute(any(Long.class))).thenReturn(healthUnitOutput);

        // Act & Assert
        mockMvc.perform(get("/health-unit/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(healthUnitOutput.getId()))
                .andExpect(jsonPath("$.name").value(healthUnitOutput.getName()))
                .andExpect(jsonPath("$.type").value(healthUnitOutput.getType().toString()))
                .andExpect(jsonPath("$.phone").value(healthUnitOutput.getPhone()))
                .andExpect(jsonPath("$.street").value(healthUnitOutput.getStreet()))
                .andExpect(jsonPath("$.number").value(healthUnitOutput.getNumber()))
                .andExpect(jsonPath("$.complement").value(healthUnitOutput.getComplement()))
                .andExpect(jsonPath("$.zipcode").value(healthUnitOutput.getZipcode()))
                .andExpect(jsonPath("$.city").value(healthUnitOutput.getCity()))
                .andExpect(jsonPath("$.state").value(healthUnitOutput.getState()))
                .andExpect(jsonPath("$.latitude").value(healthUnitOutput.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(healthUnitOutput.getLongitude()));

        verify(findHealthUnitByIdUsecase, times(1)).execute(any(Long.class));

    }

    @Test
    void shouldFindAllHealthUnitsByCityAndState() throws Exception {
        // Arrange
        List<HealthUnitOutput> healthUnits = List.of(
                HealthUnitHelper.createHealthUnitOutput(1L),
                HealthUnitHelper.createHealthUnitOutput(2L)
        );

        when(findAllHealthUnitsByCityAndStateUseCase.execute(anyString(), anyString())).thenReturn(healthUnits);

        // Act & Assert
        mockMvc.perform(get("/health-unit")
                        .queryParam("city", "São Paulo")
                        .queryParam("state", "SP"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(healthUnits.size()))
                .andExpect(jsonPath("$[0].name").value(healthUnits.get(0).getName()))
                .andExpect(jsonPath("$[1].name").value(healthUnits.get(1).getName()));

        verify(findAllHealthUnitsByCityAndStateUseCase, times(1)).execute(anyString(), anyString());
    }

    @Test
    void shouldUpdateHealthUnit() throws Exception {
        // Arrange
        Long id = 1L;
        HealthUnitInput healthUnitInput = HealthUnitHelper.createHealthUnitInput();
        HealthUnitOutput updatedHealthUnit = HealthUnitHelper.createHealthUnitOutput(id);

        when(updateHealthUnitUseCase.execute(eq(id), any(HealthUnitInput.class))).thenReturn(updatedHealthUnit);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/health-unit/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(healthUnitInput)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedHealthUnit.getId()))
                .andExpect(jsonPath("$.name").value(updatedHealthUnit.getName()))
                .andExpect(jsonPath("$.type").value(updatedHealthUnit.getType().toString()))
                .andExpect(jsonPath("$.phone").value(updatedHealthUnit.getPhone()))
                .andExpect(jsonPath("$.street").value(updatedHealthUnit.getStreet()))
                .andExpect(jsonPath("$.number").value(updatedHealthUnit.getNumber()))
                .andExpect(jsonPath("$.complement").value(updatedHealthUnit.getComplement()))
                .andExpect(jsonPath("$.zipcode").value(updatedHealthUnit.getZipcode()))
                .andExpect(jsonPath("$.city").value(updatedHealthUnit.getCity()))
                .andExpect(jsonPath("$.state").value(updatedHealthUnit.getState()))
                .andExpect(jsonPath("$.latitude").value(updatedHealthUnit.getLatitude()))
                .andExpect(jsonPath("$.longitude").value(updatedHealthUnit.getLongitude()));

        verify(updateHealthUnitUseCase, times(1)).execute(eq(id), any(HealthUnitInput.class));
    }

    @Test
    void shouldReturnBadRequestWhenCreatingInvalidHealthUnit() throws Exception {
        // Arrange
        HealthUnitInput invalidInput = HealthUnitInput.builder().build();

        // Act & Assert
        mockMvc.perform(post("/health-unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidInput)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verify(createHealthUnitUseCase, never()).execute(any(HealthUnitInput.class));
    }

    @Test
    void shouldReturnNotFoundWhenFindingNonExistentHealthUnit() throws Exception {
        // Arrange
        Long nonExistentId = 999L;
        when(findHealthUnitByIdUsecase.execute(nonExistentId))
                .thenThrow(new EntityNotFoundException("Health Unit not found"));

        // Act & Assert
        mockMvc.perform(get("/health-unit/{id}", nonExistentId))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(findHealthUnitByIdUsecase, times(1)).execute(nonExistentId);
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistentHealthUnit() throws Exception {
        // Arrange
        Long nonExistentId = 999L;
        HealthUnitInput healthUnitInput = HealthUnitHelper.createHealthUnitInput();

        when(updateHealthUnitUseCase.execute(eq(nonExistentId), any(HealthUnitInput.class)))
                .thenThrow(new EntityNotFoundException("Health Unit not found"));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/health-unit/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(healthUnitInput)))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(updateHealthUnitUseCase, times(1)).execute(eq(nonExistentId), any(HealthUnitInput.class));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}