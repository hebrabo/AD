package semana05.ejercicio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

// https://pokeapi.co/api/v2/pokemon

@Data
public class ListaPokemon {

    private int count;
    private String next;
    private String previous;

    @JsonProperty("results")
    private List<PokemonResumen> resultados;

    @Data
    public static class PokemonResumen {
        private String name;
        private String url;
    }
}