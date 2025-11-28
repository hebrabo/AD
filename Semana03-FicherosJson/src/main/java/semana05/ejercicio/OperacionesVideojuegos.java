package semana05.ejercicio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class OperacionesVideojuegos {
    private static final Path JSON_READ_FILE_PATH = Path.of(".", "src", "main", "resources", "videogames.json");
    private static final Path JSON_WRITE_FILE_PATH = Path.of(".", "src", "main", "resources", "videogames_actualizados.json");

    public static List<Videojuego> leerVideojuegosDesdeJSON(Path ruta){
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try (Reader reader = Files.newBufferedReader(ruta)){
            return objectMapper.readValue(reader, new TypeReference<>() {});
        }catch (IOException e){
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void escribirVideojuegosAJSON(List<Videojuego> videojuegos, Path ruta){
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try (Writer writer = Files.newBufferedWriter(ruta)){
            objectMapper.writeValue(writer, videojuegos);
        } catch (IOException e){
            System.out.println("Error al escribir videojuegos: " + e.getMessage());
        }

    }

    public static List<ListaPokemon.PokemonResumen> leerPrimerosPokemon(int limite) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest peticion = HttpRequest.newBuilder()
                .uri(URI.create("https://pokeapi.co/api/v2/pokemon?limit=" + limite))
                .GET()
                .build();

        try {
            HttpResponse<String> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());

            // Procesar la respuesta como JSON
            JsonNode rootNode = objectMapper.readTree(respuesta.body());

            // Leer solo el nodo "results" y mapearlo a la lista de Pokémon
            return objectMapper.readValue(
                    rootNode.get("results").traverse(),
                    new TypeReference<List<ListaPokemon.PokemonResumen>>() {}
            );

        } catch (Exception e) {
            System.out.println("Error al leer lista de Pokémon: " + e.getMessage());
            return List.of();
        }
    }

    public static void main(String[] args) {
        // Obtener los 10 primeros Pokémon
        List<ListaPokemon.PokemonResumen> pokemonList = leerPrimerosPokemon(10);

        System.out.println("Primeros " + pokemonList.size() + " Pokémon:");
        pokemonList.forEach(p ->
                System.out.println("- " + p.getName() + " (" + p.getUrl() + ")")
        );
    }
}
