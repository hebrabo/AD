package hogwarts.transacciones.main;

import hogwarts.transacciones.modelo.Asignatura;
import hogwarts.transacciones.modelo.Estudiante;
import hogwarts.transacciones.modelo.Profesor;

import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class Main {

    // Variables estáticas para la conexión
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public static void main(String[] args) {
        loadDatabaseProperties();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Conexión establecida correctamente.");

            //CASO DE PRUEBA EXITOSA
            // Insertar una nueva asignatura y luego el profesor
            System.out.println("====================================");
            System.out.println("CASO DE PRUEBA EXITOSA: Insertar asignatura y profesor");
            Asignatura asignatura = new Asignatura("Arte Muggle", "7A", false);
            Profesor profesor = new Profesor("Albus", "Dumbledore", LocalDate.now());

            int nuevoProfesorId = crearProfesorYAsignatura(conn, asignatura, profesor);
            System.out.println("Profesor insertado con ID: " + nuevoProfesorId);

            // Mostrar profesores y asignaturas tras la inserción
            System.out.println("Profesores y asignaturas tras inserción:");
            mostrarProfesoresYAsignaturas(conn);
            System.out.println();

            // CASO DE PRUEBA FALLIDA
            // Intenta insertar una asignatura y un profesor que ya existen
            System.out.println("====================================");
            System.out.println("CASO DE PRUEBA FALLIDA: Intentar insertar asignatura y profesor duplicados");
            Asignatura asignaturaExistente = new Asignatura("Defensa Contra las Artes Oscuras", "3C", true);
            Profesor profesorExistente = new Profesor("Minerva", "McGonagall", LocalDate.of(1956, 9, 1));

            int resultadoFallido = crearProfesorYAsignatura(conn, asignaturaExistente, profesorExistente);
            System.out.println("Resultado de inserción fallida (ID retornado): " + resultadoFallido);

            System.out.println("Profesores y asignaturas tras transacción fallida:");
            mostrarProfesoresYAsignaturas(conn);
            System.out.println();

            // EJECUTAR LA FUNCIÓN MATRICULAR ESTUDIANTE
            // CASO DE PRUEBA: Ejecutar función almacenada para matricular estudiante
            System.out.println("====================================");
            System.out.println("CASO DE PRUEBA: Ejecutar función almacenada para matricular estudiante");
            Estudiante selena = new Estudiante("Selena", "Shade", 4, LocalDate.of(2007, 5, 23));
            funcionMatricularEstudiante(conn, selena);
            System.out.println("Función ejecutada: matricular_estudiante para Selena Shade (curso 4)");
            System.out.println("\nFin de la prueba de matricular estudiante.");

            // CASO DE PRUEBA: Ejecutar procedimiento almacenado para insertar y matricular estudiante sin mascota
            System.out.println("====================================");
            System.out.println("CASO DE PRUEBA: Ejecutar procedimiento almacenado para insertar y matricular estudiante sin mascota");
            Estudiante theo = new Estudiante("Theo", "Blackthorn", 3, LocalDate.of(2008, 10, 11));
            procedimientoMatricularEstudiante(conn, theo);
            System.out.println("Fin de la prueba de crear_estudiante.");

            System.out.println("====================================");
            System.out.println("✔ Todas las pruebas se ejecutaron correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
    }

    // Metodo para cargar las propiedades desde db.properties
    private static void loadDatabaseProperties() {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(input);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");

            if (URL == null || USER == null || PASSWORD == null) {
                throw new SQLException("Error: propiedades de conexión no válidas.");
            }
        } catch (Exception ex) {
            System.err.println("Error al cargar el archivo de propiedades: " + ex.getMessage());
        }
    }

    /*
    * Metodo crearProfesorYAsignatura. Utilizar una transacción para insertar
    * un nuevo profesor en la base de datos y crear una asignatura que impartirá el nuevo profesor.
    */
    private static int crearProfesorYAsignatura(Connection conn, Asignatura asignatura, Profesor profesor) {
        int idAsignaturaGenerado = -1; //Para almacenar el ID de la nueva asignatura
        int idProfesorGenerado = -1; //Para almacenar el ID del nuevo profesor

        try {
            //Comenzar la transacción
            conn.setAutoCommit(false);

            String sqlAsignatura = "INSERT INTO Asignatura (nombre_asignatura, aula, obligatoria) VALUES (?, ?, ?)";
            String sqlProfesor = "INSERT INTO Profesor (nombre, apellido, id_asignatura, fecha_inicio) VALUES (?, ?, ?, ?)";

            //Inserción en la tabla Asignatura
            try (PreparedStatement pstmtAsignatura = conn.prepareStatement(sqlAsignatura, Statement.RETURN_GENERATED_KEYS)) {
                pstmtAsignatura.setString(1, asignatura.getNombreAsignatura());
                pstmtAsignatura.setString(2, asignatura.getAula());
                pstmtAsignatura.setBoolean(3, asignatura.isObligatoria());

                int filasAfectadasAsignatura = pstmtAsignatura.executeUpdate();
                System.out.println("Filas afectadas al insertar asignatura: " + filasAfectadasAsignatura);
                System.out.println("Asignatura '" + asignatura.getNombreAsignatura() + "' insertada.");

                // Obtener el ID generado de la asignatura
                try (ResultSet rsAsignatura = pstmtAsignatura.getGeneratedKeys()) {
                    if (rsAsignatura.next()) {
                        idAsignaturaGenerado = rsAsignatura.getInt(1);
                    } else {
                        throw new SQLException("No se pudo obtener el ID de la asignatura.");
                    }
                }
            }

            try (PreparedStatement pstmtProfesor = conn.prepareStatement(sqlProfesor, Statement.RETURN_GENERATED_KEYS)) {
                pstmtProfesor.setString(1, profesor.getNombre());
                pstmtProfesor.setString(2, profesor.getApellido());
                pstmtProfesor.setInt(3, idAsignaturaGenerado);
                pstmtProfesor.setDate(4, Date.valueOf(profesor.getFecha_inicio()));

                int filasAfectadasProfesor = pstmtProfesor.executeUpdate();
                System.out.println("Filas afectadas al insertar profesor: " + filasAfectadasProfesor);
                System.out.println("Profesor '" + profesor.getNombre() + " " + profesor.getApellido() + "' insertado.");

                try (ResultSet rsProfesor = pstmtProfesor.getGeneratedKeys()) {
                    if (rsProfesor.next()) {
                        idProfesorGenerado = rsProfesor.getInt(1);
                    } else {
                        throw new SQLException("No se pudo obtener el ID del profesor.");
                    }
                }
            }

            //Confirmar la transacción
            conn.commit();
            System.out.println("Profesor y asignatura insertados con éxito. ID del profesor: " + idProfesorGenerado);

        } catch (SQLException e) {
            try {
                // Si algo falla, hacemos rollback
                conn.rollback();
                System.out.println("Transacción fallida. Se ha hecho rollback.");
            } catch (SQLException rollbackEx) {
                System.err.println("Error al hacer rollback: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                // Restaurar el modo autocommit al final
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error al restaurar autocommit: " + e.getMessage());
            }
        }

        return idProfesorGenerado;
    }

    // Metodo que muestra los profesores junto con sus asignaturas.
    public static void mostrarProfesoresYAsignaturas(Connection conn) {
        try {
            String sql = "SELECT p.id_profesor, p.nombre, p.apellido, p.fecha_inicio, " +
                    "a.id_asignatura, a.nombre_asignatura, a.aula, a.obligatoria " +
                    "FROM Profesor p INNER JOIN Asignatura a ON p.id_asignatura = a.id_asignatura";

            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Asignatura asignatura = new Asignatura(
                            rs.getInt("id_asignatura"),
                            rs.getString("nombre_asignatura"),
                            rs.getString("aula"),
                            rs.getBoolean("obligatoria")
                    );

                    Profesor profesor = new Profesor(
                            rs.getInt("id_profesor"),
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            rs.getDate("fecha_inicio").toLocalDate(),
                            asignatura
                    );

                    System.out.println(profesor);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    /**
     * Ejecuta la función almacenada matricular_estudiante, que:
     * - Inserta al estudiante en la base de datos
     * - Lo matricula en asignaturas obligatorias
     * - Le asigna aleatoriamente una casa y una mascota
     * Devuelve y muestra la información del estudiante insertado.
     */

    public static void funcionMatricularEstudiante(Connection conn, Estudiante estudiante) {
        String sql = "SELECT * FROM matricular_estudiante(?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, estudiante.getNombre());
            pstmt.setString(2, estudiante.getApellido());
            pstmt.setDate(3, Date.valueOf(estudiante.getFecha_nacimiento()));
            pstmt.setInt(4, estudiante.getAnyo_curso());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\nEstudiante matriculado:");
                    System.out.println("ID: " + rs.getInt("out_id_estudiante"));
                    System.out.println("Nombre completo: " + rs.getString("nombre") + " " + rs.getString("apellido"));
                    System.out.println("Casa asignada: " + rs.getString("nombre_casa"));
                    System.out.println("Mascota asignada: " + rs.getString("mascota"));

                    mostrarAsignaturasEstudiante(conn, rs.getInt("out_id_estudiante"));
                } else {
                    System.out.println("No se devolvió información del estudiante.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al ejecutar la función matricular_estudiante: " + e.getMessage());
        }
    }

    /**
     * Muestra las asignaturas en las que está matriculado el estudiante,
     * incluyendo el nombre y el aula de cada asignatura.
     */
    public static void mostrarAsignaturasEstudiante(Connection conn, int idEstudiante) {
        String sql = "SELECT a.nombre_asignatura, a.aula " +
                "FROM Estudiante_Asignatura ea " +
                "JOIN Asignatura a ON ea.id_asignatura = a.id_asignatura " +
                "WHERE ea.id_estudiante = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEstudiante);

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Asignaturas matriculadas:");
                while (rs.next()) {
                    System.out.println("- " + rs.getString("nombre_asignatura") +
                            " (Aula: " + rs.getString("aula") + ")");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al mostrar asignaturas del estudiante: " + e.getMessage());
        }
    }

    /**
     * Ejecuta el procedimiento almacenado crear_estudiante, que:
     * - Inserta al estudiante en la base de datos
     * - Lo matricula en asignaturas obligatorias
     * - Le asigna aleatoriamente una casa
     * No devuelve datos directamente, pero se verifica la inserción.
     */
    public static void procedimientoMatricularEstudiante(Connection conn, Estudiante estudiante) {
        String sql = "CALL crear_estudiante(?, ?, ?, ?)";

        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, estudiante.getNombre());
            cstmt.setString(2, estudiante.getApellido());
            cstmt.setDate(3, Date.valueOf(estudiante.getFecha_nacimiento()));
            cstmt.setInt(4, estudiante.getAnyo_curso());

            cstmt.execute();
            System.out.println("Procedimiento almacenado ejecutado con éxito: crear_estudiante para " +
                    estudiante.getNombre() + " " + estudiante.getApellido());

            // Verificar la inserción del estudiante y sus asignaturas
            mostrarEstudiantePorNombre(conn, estudiante.getNombre(), estudiante.getApellido());

        } catch (SQLException e) {
            System.err.println("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
        }
    }

    /**
     * Busca un estudiante por nombre y apellido, y muestra su información personal,
     * incluyendo la casa asignada y las asignaturas en las que está matriculado.
     * Se utiliza para verificar la correcta inserción tras ejecutar un procedimiento.
     */
    public static void mostrarEstudiantePorNombre(Connection conn, String nombre, String apellido) {
        String sql = "SELECT e.id_estudiante, e.nombre, e.apellido, c.nombre_casa " +
                "FROM Estudiante e " +
                "JOIN Casa c ON e.id_casa = c.id_casa " +
                "WHERE e.nombre = ? AND e.apellido = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int idEstudiante = rs.getInt("id_estudiante");
                    System.out.println("\nEstudiante insertado:");
                    System.out.println("ID: " + idEstudiante);
                    System.out.println("Nombre completo: " + rs.getString("nombre") + " " + rs.getString("apellido"));
                    System.out.println("Casa asignada: " + rs.getString("nombre_casa"));

                    // Mostrar mascota si existe
                    mostrarMascotaEstudiante(conn, idEstudiante);

                    // Mostrar asignaturas
                    mostrarAsignaturasEstudiante(conn, idEstudiante);
                } else {
                    System.out.println("No se encontró al estudiante en la base de datos.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al mostrar datos del estudiante: " + e.getMessage());
        }
    }


    /**
     * Muestra la mascota asignada al estudiante, si existe.
     */
    public static void mostrarMascotaEstudiante(Connection conn, int idEstudiante) {
        String sql = "SELECT especie, nombre_mascota FROM Mascota WHERE id_estudiante = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEstudiante);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Mascota asignada:");
                    System.out.println("- Especie: " + rs.getString("especie"));
                    System.out.println("- Nombre: " + rs.getString("nombre_mascota"));
                } else {
                    System.out.println("Este estudiante no tiene mascota asignada.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar mascota del estudiante: " + e.getMessage());
        }
    }









}

