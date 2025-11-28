package semana05.ejercicio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor  // Constructor vacío
@AllArgsConstructor // Constructor con todos los campos
public class Videojuego {
    @JsonProperty("title")
    private String titulo;
    @JsonProperty("platform")
    private Plataforma plataforma;
    @JsonProperty("genre")
    private String genero;
    @JsonProperty("developers")
    private List<Desarrollador> desarrolladores;
    @JsonProperty("releaseDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fechaLanzamiento;
    @JsonProperty("reviews")
    private List<Review> reviews;
}
