package modelos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reproduccion {

    @JsonProperty("contenido")
    private String contenido;

    @JsonProperty("fecha")
    // Añado 'T'HH:mm:ss para coincidir con "2024-11-01T20:30:00"
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime fecha;

}
