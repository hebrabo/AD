package org.accesodatos.semana17Testing.services;

import org.accesodatos.semana17Testing.models.Estudiante;
import org.accesodatos.semana17Testing.models.Mascota;
import org.accesodatos.semana17Testing.repositories.EstudianteRepository;
import org.accesodatos.semana17Testing.services.impl.EstudianteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Inicializa Mockito sin cargar Spring
class EstudianteServiceTest {

    @Mock // Crea un objeto simulado EstudianteRepository vacío
    private EstudianteRepository estudianteRepository;

    @InjectMocks // Crea la instancia del servicio e inyecta el mock dentro
    private EstudianteServiceImpl estudianteService;

    private Estudiante harryPotter;

    @BeforeEach
    void setUp() {
        // Preparamos a Harry Potter para las pruebas
        harryPotter = new Estudiante();
        harryPotter.setIdEstudiante(1L);
        harryPotter.setNombre("Harry Potter");
        harryPotter.setApellido("Griffin");
        harryPotter.setMascota(null); // Empezamos sin mascota
    }

    @Test
    void borrarEstudiante_Exito_SinMascota() {
        // GIVEN
        Long id = 1L;
        when(estudianteRepository.findById(id)).thenReturn(Optional.of(harryPotter));

        // WHEN
        estudianteService.borrarEstudiante(id);

        // THEN
        verify(estudianteRepository, times(1)).delete(harryPotter);
    }

    @Test
    void borrarEstudiante_Error_ConMascota() {
        // GIVEN (Preparación)
        Long id = 1L;

        harryPotter.setMascota(new Mascota());
        when(estudianteRepository.findById(id)).thenReturn(Optional.of(harryPotter));

        // WHEN & THEN (Ejecución y Verificación de la excepción)
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            estudianteService.borrarEstudiante(id);
        });

        // Verificamos que el mensaje sea EXACTAMENTE el mismo que en el Service
        assertEquals("No se puede expulsar a un estudiante con mascota activa.", exception.getMessage());

        // Verificamos la seguridad: el repositorio NUNCA recibe el delete
        verify(estudianteRepository, never()).delete(any(Estudiante.class));
    }
}