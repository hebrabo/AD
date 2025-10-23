package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Operaciones {

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
}
