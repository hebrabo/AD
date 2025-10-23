package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class Main {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public static void main(String[] args) {

        // Cargar las propiedades de conexión
        loadDatabaseProperties();

        // Conectar a la base de datos
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Conexión establecida correctamente");

            // Llamar al metodo que devuelve las asignaturas
            System.out.println("---- LISTADO DE ASIGNATURAS: -----");
            List<Asignatura> lista = Operaciones.obtenerAsignaturas(conn);
            for (Asignatura a : lista) {
                System.out.println(a);
            }

            // Llamar al metodo que muestra estudiantes por casa
            Operaciones op = new Operaciones();
            op.consultarEstudiantesPorCasa(conn, "Gryffindor");



        } catch (SQLException e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void loadDatabaseProperties() {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(input);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
        } catch (IOException ex) {
            System.err.println("Error al cargar db.properties: " + ex.getMessage());
        }
    }
}


