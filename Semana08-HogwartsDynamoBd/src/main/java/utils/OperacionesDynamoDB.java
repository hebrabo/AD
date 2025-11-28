package utils;

import modelos.Casa;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
import software.amazon.awssdk.services.dynamodb.model.DescribeTableRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

public class OperacionesDynamoDB {

    private final DynamoDbClient dynamoDbClient;
    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;

    public OperacionesDynamoDB() {
        this.dynamoDbClient = DynamoDBManager.getDynamoDbClient();
        this.dynamoDbEnhancedClient = DynamoDBManager.getEnhancedClient();
    }

    public void crearTabla(String nombreTabla) {
        if (tablaExiste(nombreTabla)) {
            System.out.println("La tabla ya existe: " + nombreTabla);
            return;
        }

        // Crear la tabla usando la clase de esquema de la entidad Casa
        dynamoDbEnhancedClient.table(nombreTabla, TableSchema.fromBean(Casa.class)).createTable();
        System.out.println("Creando tabla: " + nombreTabla);

        // Esperar hasta que la tabla esté activa
        DynamoDbWaiter waiter = dynamoDbClient.waiter();
        waiter.waitUntilTableExists(DescribeTableRequest.builder().tableName(nombreTabla).build());
        System.out.println("Tabla '" + nombreTabla + "' creada y activa.");
    }

    private boolean tablaExiste (String nombreTabla) {
        try {
            // Intenta describir la tabla. Si no existe, se lanzará una excepción
            dynamoDbClient.describeTable(r -> r.tableName(nombreTabla));
            return true;
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }

    public void imprimirTodasLasCasas() {
        DynamoDbTable<Casa> tabla = dynamoDbEnhancedClient.table("CasasHogwarts", TableSchema.fromBean(Casa.class));
        // Crear una solicitud de escaneo para obtener todos los elementos
        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder().build();
        // Escanear la tabla y obtener un iterable de páginas de resultados
        PageIterable<Casa> casas = tabla.scan(scanRequest);
        // Iterar sobre cada página y luego sobre cada elemento de casa
        casas.items().forEach(System.out::println);
    }

    public void insertarCasa(Casa casa) {
        // Obtener la tabla para realizar operaciones
        DynamoDbTable<Casa> tabla = dynamoDbEnhancedClient.table("CasasHogwarts", TableSchema.fromBean(Casa.class));
        // Insertar la casa en la tabla
        tabla.putItem(casa);
        System.out.println("Casa insertada correctamente: " + casa.getNombre());
    }

    public Casa obtenerCasa(String nombreCasa) {
        DynamoDbTable<Casa> tabla = dynamoDbEnhancedClient.table("CasasHogwarts", TableSchema.fromBean(Casa.class));
        // Crear la clave de búsqueda
        Key key = Key.builder().partitionValue(nombreCasa).build();
        // Obtener la casa usando la clave
        Casa casa = tabla.getItem(r -> r.key(key));
        if (casa != null) {
            System.out.println("Casa encontrada: " + casa.getNombre());
        } else {
            System.out.println("Casa no encontrada");
        }
        return casa;
    }

    public void actualizarCasa(Casa casa) {
        // Primero, verificar si la casa existe antes de actualizar
        if (obtenerCasa(casa.getNombre()) != null) {
            DynamoDbTable<Casa> tabla = dynamoDbEnhancedClient.table("CasasHogwarts", TableSchema.fromBean(Casa.class));
            // Actualizar la casa en la tabla
            tabla.updateItem(casa);
            System.out.println("Casa actualizada: " + casa.getNombre());
        } else {
            System.out.println("La casa no existe y no se puede actualizar: " + casa.getNombre());
        }
    }

    public void borrarCasa(String nombre) {
        DynamoDbTable<Casa> tabla = dynamoDbEnhancedClient.table("CasasHogwarts", TableSchema.fromBean(Casa.class));
        // Crear la clave de la casa borrar
        Key key = Key.builder().partitionValue(nombre).build();
        // Borrar la casa de la tabla
        tabla.deleteItem(r -> r.key(key));
        System.out.println("Casa borrada: " + nombre);
    }

    public void borrarTabla(String nombreTabla) {
        // Verificar si la tabla existe antes de intentar borrarla
        if (!tablaExiste(nombreTabla)) {
            System.out.println("La tabla no existe: " + nombreTabla);
            return;
        }

        // Solicitud para borrar la tabla
        dynamoDbClient.deleteTable(DeleteTableRequest.builder().tableName(nombreTabla).build());
        System.out.println("Eliminando tabla: " + nombreTabla);

        // Esperar hasta que la tabla ya no exista
        DynamoDbWaiter waiter = dynamoDbClient.waiter();
        waiter.waitUntilTableNotExists(DescribeTableRequest.builder().tableName(nombreTabla).build());
        System.out.println("Tabla '" + nombreTabla + "' eliminada.");
    }

}
