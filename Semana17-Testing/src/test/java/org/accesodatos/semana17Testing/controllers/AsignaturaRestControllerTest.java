package org.accesodatos.semana17Testing.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.accesodatos.semana17Testing.dtos.response.AsignaturaDTO;
import org.accesodatos.semana17Testing.services.AsignaturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AsignaturaRestController.class)
public class AsignaturaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AsignaturaService asignaturaService;

    @Autowired
    private ObjectMapper objectMapper;

    private AsignaturaDTO asignaturaResponseDTO;

    @BeforeEach
    void setUp() {
        // Preparamos la respuesta simulada
        asignaturaResponseDTO = new AsignaturaDTO();
        asignaturaResponseDTO.setId(1L);
        asignaturaResponseDTO.setNombre("Defensa contra las Artes oscuras");
    }

    @Test
    void eliminarAsignatura_DebeRetornar204_Exito() throws Exception {
        // GIVEN: El servicio no hace nada (éxito)
        doNothing().when(asignaturaService).borrarAsignatura(1L);

        // WHEN & THEN
        mockMvc.perform(delete("/api/asignaturas/1"))
                .andExpect(status().isNoContent()); // Verifica 204 No Content

    }

    @Test
    void eliminarAsignatura_ConAlumnos_DebeRetornar409() throws Exception {
        // GIVEN: El servicio lanza la excepción que definimos
        doThrow(new IllegalStateException("No se puede eliminar una asignatura con alumnos matriculados"))
                .when(asignaturaService).borrarAsignatura(1L);

        // WHEN & THEN
        mockMvc.perform(delete("/api/asignaturas/1"))
                .andExpect(status().isConflict()); // Verifica 409 Conflict

    }
}