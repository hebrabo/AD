package modelos;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@Data
@NoArgsConstructor
@DynamoDbBean
public class Estudiante {
    private String nombre;
    private int curso;
    private String fechaNacimiento;
    private String mascota;

    @DynamoDbAttribute("nombre")
    public String getNombre() {
        return nombre;
    }

    @DynamoDbAttribute("curso")
    public int getCurso() {
        return curso;
    }

    @DynamoDbAttribute("fechaNacimiento")
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    @DynamoDbAttribute("mascota")
    public String getMascota() {
        return mascota;
    }
}