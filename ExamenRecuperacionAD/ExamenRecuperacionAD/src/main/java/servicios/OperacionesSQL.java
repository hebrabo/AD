package servicios;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import modelos.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;

public class OperacionesSQL {
    private static final Path RUTA_USUARIOS = Path.of(".", "src", "main", "resources", "usuarios.json");
    private static final Path RUTA_HISTORIAL = Path.of(".", "src", "main", "resources", "historial.json");


    // 1. LECTURA DE FICHEROS (Jackson)
    public static List<Usuario> leerUsuariosDeJson(Path ruta) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try (Reader reader = Files.newBufferedReader(ruta)) {
            return objectMapper.readValue(reader, new TypeReference<>() {});
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de usuarios: " + e.getMessage());
            return List.of();
        }
    }

    public static List<Historial> leerHistorialDeJson(Path ruta) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try (Reader reader = Files.newBufferedReader(ruta)) {
            return objectMapper.readValue(reader, new TypeReference<List<Historial>>() {});
        } catch (IOException e) {
            System.out.println("Error al leer historial: " + e.getMessage());
            return List.of();
        }
    }

    // 2.1. POSTGRESQL: ALMACENAMIENTO

    public static int insertarUsuario(Connection conn, String nombre, String email) {
        int idGenerado = -1;
        try {
            String sql = "INSERT INTO usuarios (nombre_completo, email) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, nombre);
            pstmt.setString(2, email);

            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Filas afectadas al insertar: " + filasAfectadas);

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            System.out.println("Usuario insertado con ID: " + idGenerado);
        } catch (SQLException e) {
            System.err.println("Error al insertar el usuario: " + e.getMessage());
            return -1;
        }
        return idGenerado;
    }

    public static void insertarReproduccion(Connection conn, int idUsuario, Reproduccion repro) {
        try {
            String sql = "INSERT INTO contenidos_reproducidos (id_usuario, contenido, fecha) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, idUsuario);
            pstmt.setString(2, repro.getContenido());
            // Convertimos LocalDateTime a Timestamp para PostgreSQL
            pstmt.setTimestamp(3, Timestamp.valueOf(repro.getFecha()));

            int filasAfectadas = pstmt.executeUpdate();
            System.out.println("Filas afectadas al insertar reproducción: " + filasAfectadas);

            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error al insertar la reproducción: " + e.getMessage());
        }
    }

    // 2.2. POSTGRESQL: CONSULTAS
    /*
     * 2.2.1. Obtener los contenidos reproducidos para un usuario específico.
     * List<String> obtenerContenidosPorUsuario(String email)
     */
    public static List<String> obtenerContenidosPorUsuario(Connection conn, String email) {
        List<String> contenidos = new ArrayList<>();
        try {
            String sql = "SELECT cr.contenido FROM contenidos_reproducidos cr " +
                    "JOIN usuarios u ON cr.id_usuario = u.id_usuario " +
                    "WHERE u.email = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            System.out.println("Ejecutando consulta segura: " + sql);

            while (rs.next()) {
                contenidos.add(rs.getString("contenido"));
                System.out.println("Contenido: " + rs.getString("contenido"));
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println("Error al obtener contenidos por usuario: " + e.getMessage());
        }
        return contenidos;
    }

    /*
     * 2.2.2. Listar los emails de usuarios que han reproducido un contenido determinado.
     * List<String> obtenerUsuariosPorContenido(String contenido)
     */
    public static List<String> obtenerUsuariosPorContenido(Connection conn, String contenido) {
        List<String> emails = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT u.email FROM usuarios u " +
                    "JOIN contenidos_reproducidos cr ON u.id_usuario = cr.id_usuario " +
                    "WHERE cr.contenido = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, contenido);

            ResultSet rs = pstmt.executeQuery();
            System.out.println("Ejecutando consulta segura: " + sql);

            while (rs.next()) {
                emails.add(rs.getString("email"));
                System.out.println("Email: " + rs.getString("email"));
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios por contenido: " + e.getMessage());
        }
        return emails;
    }

    // 3. FUNCIONALIDAD ADICIONAL
    /*
     * Borra los usuarios y sus reproducciones en ambas bases de datos,
     * respetando las relaciones y referencias.
     * void borrarUsuarioPorEmail(String email);
     */
    public static void borrarUsuarioPorEmail(Connection conn, String email) {
        try {
            // 3.1. Borramos las reproducciones (hijos)
            String sqlHijos = "DELETE FROM contenidos_reproducidos WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE email = ?)";
            PreparedStatement pstmtHijos = conn.prepareStatement(sqlHijos);
            pstmtHijos.setString(1, email);
            pstmtHijos.executeUpdate();
            pstmtHijos.close();

            // 3.2. Borramos al usuario (padre)
            String sqlBorrarUsuario = "DELETE FROM usuarios WHERE email = ?";
            PreparedStatement pstmtUsuario = conn.prepareStatement(sqlBorrarUsuario);
            pstmtUsuario.setString(1, email);
            pstmtUsuario.executeUpdate();
            pstmtUsuario.close();

            System.out.println("Usuario (" + email + ") y sus reproducciones borrados con éxito.");

        } catch (SQLException e) {
            System.err.println("Error al borrar el usuario con email " + email + ": " + e.getMessage());
        }
    }

}
