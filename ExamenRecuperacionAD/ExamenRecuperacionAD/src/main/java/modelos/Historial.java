package modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Historial {

    @JsonProperty("usuario")
    private String correo;

    @JsonProperty("reproducciones")
    private List<Reproduccion> reproducciones;

}
