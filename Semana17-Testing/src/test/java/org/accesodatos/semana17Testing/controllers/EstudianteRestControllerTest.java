package org.accesodatos.semana17Testing.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.accesodatos.semana17Testing.dtos.request.create.EstudianteCreateDTO;
import org.accesodatos.semana17Testing.dtos.request.create.MascotaCreateDTO;
import org.accesodatos.semana17Testing.dtos.response.EstudianteDTO;
import org.accesodatos.semana17Testing.services.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstudianteRestController.class)
public class EstudianteRestControllerTest {

    @Autowired
    private MockMvc mockMvc; // Para realizar peticiones HTTP

    @MockitoBean
    private EstudianteService estudianteService; // Moqueamos el servicio para no necesitar base de datos

    @Autowired
    private ObjectMapper objectMapper;

    private EstudianteCreateDTO estudianteCreateDTO;
    private EstudianteDTO estudianteResponseDTO;

    @BeforeEach
    void setUp() {
        // 1. Preparamos la Mascota DTO (parte del request)
        MascotaCreateDTO mascotaDTO = new MascotaCreateDTO();
        mascotaDTO.setNombre("Hedwig");
        mascotaDTO.setEspecie("Buho");

        // 2. Preparamos el Estudiante Create DTO (request principal)
        estudianteCreateDTO = new EstudianteCreateDTO();
        estudianteCreateDTO.setNombre("Harry");
        estudianteCreateDTO.setApellido("Potter");
        estudianteCreateDTO.setAnyoCurso(1);
        estudianteCreateDTO.setFechaNacimiento(LocalDate.of(1980, 7, 31));
        estudianteCreateDTO.setCasaId(1L);
        estudianteCreateDTO.setMascota(mascotaDTO); // Vinculación

        // 3. Respuesta simulada del servicio
        estudianteResponseDTO = new EstudianteDTO();
        estudianteResponseDTO.setId(1L);
        estudianteResponseDTO.setNombre("Harry");
    }

    @Test
    void crearEstudiante_Exito() throws Exception {
        // GIVEN
        when(estudianteService.crearEstudiante(any(EstudianteCreateDTO.class))).thenReturn(estudianteResponseDTO);

        // WHEN & THEN
        mockMvc.perform(post("/api/estudiantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estudianteCreateDTO)))
                .andExpect(status().isCreated()) // Verifica que devuelve 201
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Harry"));

        verify(estudianteService).crearEstudiante(any(EstudianteCreateDTO.class));
    }

    @Test
    void crearEstudiante_CursoInvalido() throws Exception {
        // GIVEN: El curso máximo es 7 y mandamos 10
        estudianteCreateDTO.setAnyoCurso(10);

        // WHEN & THEN
        mockMvc.perform(post("/api/estudiantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(estudianteCreateDTO)))
                .andExpect(status().isBadRequest()); // Verifica 400 Bad Request

        // El servicio nunca debe ser llamado si los DTOs fallan la validación
        verify(estudianteService, never()).crearEstudiante(any());
    }

    @Test
    void borrarEstudiante_DebeRetornar204() throws Exception {
        // GIVEN
        doNothing().when(estudianteService).borrarEstudiante(1L);

        // WHEN & THEN
        mockMvc.perform(delete("/api/estudiantes/1"))
                .andExpect(status().isNoContent()); // Verifica 204 No Content
    }

    @Test
    void borrarEstudiante_ConMascota_DebeRetornar409() throws Exception {
        // GIVEN: El servicio lanza la excepción que definimos
        doThrow(new IllegalStateException("No se puede expulsar a un estudiante con mascota activa."))
                .when(estudianteService).borrarEstudiante(1L);

        // WHEN & THEN
        mockMvc.perform(delete("/api/estudiantes/1"))
                .andExpect(status().isConflict()); // Verifica 409 Conflict
    }
}