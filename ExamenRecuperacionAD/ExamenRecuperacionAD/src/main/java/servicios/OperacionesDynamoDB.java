package servicios;

import admin.DynamoDbAdmin;
import modelos.HistorialDynamo;
import modelos.ReproduccionDynamo;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.ArrayList;
import java.util.List;

public class OperacionesDynamoDB {

    private final DynamoDbEnhancedClient enhancedClient;
    private final String NOMBRE_TABLA = "helbrabou-historial";

    public OperacionesDynamoDB() {
        this.enhancedClient = DynamoDbAdmin.getEnhancedClient();
    }

    /*
     * 1. Almacena el historial completo de reproducciones en formato no relacional.
     */
    public void insertarHistorial(HistorialDynamo historial) {
        DynamoDbTable<HistorialDynamo> tabla = enhancedClient.table(NOMBRE_TABLA, TableSchema.fromBean(HistorialDynamo.class));
        tabla.putItem(historial);
        System.out.println("Historial insertado correctamente: " + historial.getEmail());
    }

    /**
     * --- CONSULTAS ---
     * 2.1. Obtener el historial completo de un usuario.
     * List<Reproduccion> obtenerHistorialPorUsuario(String email);
     */
    public List<ReproduccionDynamo> obtenerHistorialPorUsuario(String email) {
        DynamoDbTable<HistorialDynamo> tabla = enhancedClient.table(NOMBRE_TABLA, TableSchema.fromBean(HistorialDynamo.class));

        for (HistorialDynamo h : tabla.scan().items()) {
            if (h.getEmail() != null && h.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Historial encontrado para el usuario: " + email);
                return h.getReproducciones();
            }
        }
        System.out.println("Historial no encontrado para: " + email);
        return new ArrayList<>();
    }

    /**
     * 2.2. Contar las veces que un contenido específico ha sido reproducido.
     * int contarReproduccionesPorContenido(String contenido);
     */
    public int contarReproduccionesPorContenido(String contenido) {
        DynamoDbTable<HistorialDynamo> tabla = enhancedClient.table(NOMBRE_TABLA, TableSchema.fromBean(HistorialDynamo.class));
        int contador = 0;

        for (HistorialDynamo h : tabla.scan().items()) {
            if (h.getReproducciones() != null) {
                for (ReproduccionDynamo r : h.getReproducciones()) {
                    if (r.getContenido().equalsIgnoreCase(contenido)) {
                        contador++;
                    }
                }
            }
        }
        return contador;
    }

    /**
     * 3. Funcionalidad adicional: Borra los usuarios y sus reproducciones en ambas bases de datos,
     * respetando las relaciones y referencias.
     * void borrarUsuarioPorEmail(String email);
     */
    public void borrarUsuarioPorEmail(String email) {
        DynamoDbTable<HistorialDynamo> tabla = enhancedClient.table(NOMBRE_TABLA, TableSchema.fromBean(HistorialDynamo.class));

        for (HistorialDynamo h : tabla.scan().items()) {
            if (h.getEmail() != null && h.getEmail().equalsIgnoreCase(email)) {
                software.amazon.awssdk.enhanced.dynamodb.Key key = software.amazon.awssdk.enhanced.dynamodb.Key.builder()
                        .partitionValue(h.getIdUsuario())
                        .build();
                tabla.deleteItem(r -> r.key(key));
                System.out.println("Historial borrado: " + email);
                return;
            }
        }
        System.out.println("No se encontró historial para borrar: " + email);
    }

    public void crearTabla(String nombreTabla) {
        DynamoDbAdmin.crearTabla(nombreTabla);
    }

    public void borrarTabla(String nombreTabla) {
        DynamoDbAdmin.borrarTabla(nombreTabla);
    }
}