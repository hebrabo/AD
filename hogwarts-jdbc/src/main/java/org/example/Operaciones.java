package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Operaciones {

    // // Metodo que obtiene todas las asignaturas registradas en la base de datos.
    public static List<Asignatura> obtenerAsignaturas(Connection conn) {
        List<Asignatura> asignaturas = new ArrayList<>();
        String sql = "SELECT id_asignatura, nombre_asignatura, aula, obligatoria FROM Asignatura";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_asignatura");
                String nombre = rs.getString("nombre_asignatura");
                String aula = rs.getString("aula");
                boolean obligatoria = rs.getBoolean("obligatoria");

                Asignatura asignatura = new Asignatura(id, nombre, aula, obligatoria);
                asignaturas.add(asignatura);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener asignaturas: " + e.getMessage());
        }

        return asignaturas;
    }

    /**
     * Metodo que consulta los nombres y apellidos de todos los estudiantes
     * que pertenecen a una casa específica
     */
    public void consultarEstudiantesPorCasa(Connection conn, String nombreCasa) {
        try {
            String sql = "SELECT e.nombre, e.apellido " +
                    "FROM Estudiante e " +
                    "JOIN Casa c ON e.id_casa = c.id_casa " +
                    "WHERE c.nombre_casa = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombreCasa);

            ResultSet rs = pstmt.executeQuery();

            System.out.println("---- ESTUDIANTES DE LA CASA " + nombreCasa.toUpperCase() + ":" + " ----");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                System.out.println(nombre + " " + apellido);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println("Error al consultar estudiantes por casa: " + e.getMessage());
        }
    }

}
