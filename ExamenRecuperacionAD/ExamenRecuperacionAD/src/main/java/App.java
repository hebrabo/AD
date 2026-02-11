import modelos.*;
import servicios.OperacionesDynamoDB;
import servicios.OperacionesSQL;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static servicios.OperacionesSQL.*;

public class App {

    private static String URL;
    private static String USER;
    private static String PASSWORD;

    private static final Path RUTA_USUARIOS = Path.of(".", "src", "main", "resources", "usuarios.json");
    private static final Path RUTA_HISTORIAL = Path.of(".", "src", "main", "resources", "historial.json");
    private static final String NOMBRE_TABLA = "helbrabou-historial";

    public static void main(String[] args) {

        // Cargar las propiedades de conexión SQL
        loadDatabaseProperties();

        // Inicializamos el servicio de DynamoDB
        OperacionesDynamoDB opsDynamo = new OperacionesDynamoDB();

        // --- 0. PREPARACIÓN  ---
        System.out.println("--- 0. PREPARANDO TABLA DYNAMODB ---");
        opsDynamo.borrarTabla(NOMBRE_TABLA);
        opsDynamo.crearTabla(NOMBRE_TABLA);

        // --- 1. LECTURA DE FICHEROS JSON (Jackson) ---
        System.out.println("\n--- 1. LEYENDO DATOS DE JSON ---");
        List<Usuario> listaUsuarios = leerUsuariosDeJson(RUTA_USUARIOS);
        List<Historial> listaHistoriales = leerHistorialDeJson(RUTA_HISTORIAL);

        // --- 2. OPERACIONES DE BASE DE DATOS ---
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // 2.1. INSERCIÓN DE DATOS
            System.out.println("\n--- 2.1. INSERTANDO DATOS ---");
            for (Usuario u : listaUsuarios) {
                int nuevoId = insertarUsuario(conn, u.getNombre(), u.getEmail());

                if (nuevoId != -1) {
                    for (Historial h : listaHistoriales) {
                        if (h.getCorreo().equalsIgnoreCase(u.getEmail())) {

                            HistorialDynamo hDyn = new HistorialDynamo();
                            hDyn.setIdUsuario(nuevoId);
                            hDyn.setEmail(u.getEmail());
                            List<ReproduccionDynamo> listaReproDyn = new ArrayList<>();

                            for (Reproduccion repro : h.getReproducciones()) {
                                insertarReproduccion(conn, nuevoId, repro);

                                ReproduccionDynamo rd = new ReproduccionDynamo();
                                rd.setContenido(repro.getContenido());
                                rd.setFecha(repro.getFecha().toString());
                                listaReproDyn.add(rd);
                            }

                            hDyn.setReproducciones(listaReproDyn);
                            opsDynamo.insertarHistorial(hDyn);
                        }
                    }
                }
            }

            // 2.2. CONSULTAS
            System.out.println("\n--- 2.2. EJECUCIÓN DE CONSULTAS ---");

            // Consultas en SQL
            System.out.println("Contenidos vistos por paulinaencinas@gmail.com (SQL):");
            obtenerContenidosPorUsuario(conn, "paulinaencinas@gmail.com");

            System.out.println("\nUsuarios que han visto 'House of Cards' (SQL):");
            obtenerUsuariosPorContenido(conn, "House of Cards");

            // Consultas en DynamoDB
            System.out.println("\n--- CONSULTAS DYNAMODB ---");
            int totalBreakingBad = opsDynamo.contarReproduccionesPorContenido("Breaking Bad");
            System.out.println("Total reproducciones 'Breaking Bad' en la nube: " + totalBreakingBad);

            List<ReproduccionDynamo> historialPaulina = opsDynamo.obtenerHistorialPorUsuario("paulinaencinas@gmail.com");
            System.out.println("Paulina ha visto " + historialPaulina.size() + " contenidos en Dynamo.");

            // --- 3. BORRADO DE AMBAS BBDD (Funcionalidad Adicional) ---
            System.out.println("\n--- 3. INICIANDO PROCESO DE BORRADO ---");
            for (Usuario usuario : listaUsuarios) {
                String email = usuario.getEmail();

                System.out.println("Comprobando y borrando datos de: " + email);

                OperacionesSQL.obtenerContenidosPorUsuario(conn, email);

                OperacionesSQL.borrarUsuarioPorEmail(conn, email);

                opsDynamo.borrarUsuarioPorEmail(email);
            }

        } catch (SQLException e) {
            System.err.println("Error en la conexión o ejecución SQL: " + e.getMessage());
        }
    }

    public static void loadDatabaseProperties() {
        Properties properties = new Properties();
        try (InputStream input = App.class.getClassLoader().getResourceAsStream("rds.properties")) {
            properties.load(input);
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
        } catch (IOException ex) {
            System.err.println("Error al cargar rds.properties: " + ex.getMessage());
        }
    }
}