package utils;

import modelos.Casa;
import modelos.Estudiante;
import modelos.Profesor;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        OperacionesDynamoDB operacionesDynamoDB = new OperacionesDynamoDB();

        // Crear la tabla "CasasHogwarts"
        operacionesDynamoDB.crearTabla("CasasHogwarts");

        OperacionesDynamoDB operaciones = new OperacionesDynamoDB();

        // Crear el profesor jefe
        Profesor jefe = new Profesor();
        jefe.setNombre("Aldous Nightshade");
        jefe.setAsignatura("Magia Arcana");

        // Crear estudiantes iniciales
        Estudiante est1 = new Estudiante();
        est1.setNombre("Selena Shade");
        est1.setCurso(4);
        est1.setFechaNacimiento("2007-05-23");
        est1.setMascota("Umbra");

        Estudiante est2 = new Estudiante();
        est2.setNombre("Theo Blackthorn");
        est2.setCurso(3);
        est2.setFechaNacimiento("2008-10-11");

        Estudiante est3 = new Estudiante();
        est3.setNombre("Luna Ashwood");
        est3.setCurso(5);
        est3.setFechaNacimiento("2006-01-17");
        est3.setMascota("Mistral");

        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(est1);
        estudiantes.add(est2);
        estudiantes.add(est3);

        // Crear la casa Dracorfan
        Casa casa = new Casa();
        casa.setNombre("Dracorfan");
        casa.setFundador("Leonis Dracorfan");
        casa.setFantasma("La Sombra de Ébano");
        casa.setJefe(jefe);
        casa.setEstudiantes(estudiantes);

        // Insertar la casa en DynamoDB
        operaciones.insertarCasa(casa);

        // Mostrar todas las casas
        System.out.println("\n--- Casas en DynamoDB ---");
        operaciones.imprimirTodasLasCasas();

        // Agregar un nuevo estudiante
        Estudiante nuevoEst = new Estudiante();
        nuevoEst.setNombre("Cyrus Stormrider");
        nuevoEst.setCurso(1);
        nuevoEst.setFechaNacimiento("2011-12-20");
        nuevoEst.setMascota("Tempus");

        casa.getEstudiantes().add(nuevoEst);

        // Actualizar la casa con el nuevo estudiante
        operaciones.actualizarCasa(casa);

        // Mostrar la casa actualizada
        System.out.println("\n--- Casa actualizada ---");
        Casa dracorfan = operaciones.obtenerCasa("Dracorfan");
        System.out.println(dracorfan);

        // Borrar la casa de prueba
        operaciones.borrarCasa("Dracorfan");

        // Comprobar que se borró
        System.out.println("\n--- Comprobando borrado ---");
        operaciones.imprimirTodasLasCasas();

        // Borrar la tabla al finalizar
        operacionesDynamoDB.borrarTabla("CasasHogwarts");

    }
}
