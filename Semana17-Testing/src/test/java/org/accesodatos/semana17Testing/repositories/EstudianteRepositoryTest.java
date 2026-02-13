package org.accesodatos.semana17Testing.repositories;

import jakarta.persistence.EntityManager;
import org.accesodatos.semana17Testing.models.Estudiante;
import org.accesodatos.semana17Testing.models.Mascota;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest // Configura una BD en memoria y carga solo entidades y repositorios
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@org.springframework.test.context.TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class EstudianteRepositoryTest {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private EntityManager entityManager; // Usamos el motor de JPA para consultas directas

    @Test
    void eliminarEstudiante_DebeEliminarMascotaEnCascada() {
        // 1. GIVEN: Preparamos los datos
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Harry");
        estudiante.setApellido("Potter");
        estudiante.setAnyoCurso(1);
        estudiante.setFechaNacimiento(LocalDate.of(1980, 7, 31));

        Mascota mascota = new Mascota();
        mascota.setNombre("Hedwig");
        mascota.setEspecie("Buho");

        // Sincronizamos la relación bidireccional
        estudiante.setMascota(mascota);
        mascota.setEstudiante(estudiante);

        // Persistimos el estudiante (y por cascada, la mascota)
        Estudiante guardado = estudianteRepository.save(estudiante);
        Long idMascota = guardado.getMascota().getIdMascota();

        // 2. WHEN: Ejecutamos la acción de borrado
        estudianteRepository.delete(guardado);

        /*
            IMPORTANTE: Forzamos el volcado a la BD y limpiamos la caché.
            Si no hacemos esto, entityManager.find() podría devolver el objeto
            que aún reside en la memoria (caché de primer nivel) de JPA.
        */
        estudianteRepository.flush();
        entityManager.clear();

        // 3. THEN: Verificaciones
        // Comprobamos que el estudiante ya no existe usando el repositorio
        assertFalse(estudianteRepository.findById(guardado.getIdEstudiante()).isPresent());

        // Comprobamos que la mascota ya no existe usando el EntityManager directamente
        // al no tener una interfaz MascotaRepository para esta comprobación
        Mascota mascotaEnBD = entityManager.find(Mascota.class, idMascota);
        assertNull(mascotaEnBD, "La mascota debería haber sido borrada por la cascada de JPA");
    }
}