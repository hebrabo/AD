package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Operaciones {

    /*
    * Metodo que obtiene
    * todas las asignaturas registradas en la base de datos.
     */
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

    /*
     * Metodo que consulta los nombres y apellidos de todos los estudiantes
     * que pertenecen a una casa específica
     */
    public static void consultarEstudiantesPorCasa(Connection conn, String nombreCasa) {
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

    /*
     * Metodo que consulta la mascota asociada a un estudiante específico
     * por nombre y apellido
     */

    public static void mostrarMascotaDeEstudiante(Connection conn, String nombre, String apellido){
        try {
            String sql = "SELECT m.nombre_mascota, m.especie " +
                    "FROM Estudiante e " +
                    "JOIN Mascota m ON e.id_estudiante = m.id_estudiante " +
                    "WHERE e.nombre = ? AND e.apellido = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);

            ResultSet rs = pstmt.executeQuery();

            System.out.println("---- MASCOTA DE " + nombre.toUpperCase() + " " + apellido.toUpperCase() + ":" + " ----");
            if (rs.next()) {
                String nombreMascota = rs.getString("nombre_mascota");
                String especie = rs.getString("especie");
                System.out.println(nombreMascota + " (" + especie + ")");
            } else {
                System.out.println("No se encontró mascota para ese estudiante.");
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println("Error al consultar la mascota: " + e.getMessage());
        }
    }

    /*
     * Metodo que consulta y devuelve
     * el número de estudiantes en cada casa de Hogwarts.
     */

    public static void mostrarNumeroEstudiantesPorCasa(Connection conn) {
        try {
            String sql = "SELECT c.nombre_casa, COUNT(e.id_estudiante) AS total_estudiantes " +
                    "FROM Casa c " +
                    "LEFT JOIN Estudiante e ON c.id_casa = e.id_casa " +
                    "GROUP BY c.nombre_casa " +
                    "ORDER BY c.nombre_casa";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("---- NÚMERO DE ESTUDIANTES POR CASA: ----");
            while (rs.next()) {
                String nombreCasa = rs.getString("nombre_casa");
                int total = rs.getInt("total_estudiantes");
                System.out.println(nombreCasa + ": " + total);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println("Error al contar estudiantes por casa: " + e.getMessage());
        }
    }







}
