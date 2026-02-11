package modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Usuario {

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("email")
    private String email;
}
