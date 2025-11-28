package modelos;

import lombok.Data;
import lombok.NoArgsConstructor;
import modelos.Estudiante;
import modelos.Profesor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@Data
@NoArgsConstructor
@DynamoDbBean
public class Casa {
    private String nombre;
    private String fundador;
    private String fantasma;
    private Profesor jefe;
    private List<Estudiante> estudiantes;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("nombre")
    public String getNombre() {
        return nombre;
    }

    @DynamoDbAttribute("fundador")
    public String getFundador() {
        return fundador;
    }

    @DynamoDbAttribute("fantasma")
    public String getFantasma() {
        return fantasma;
    }

    @DynamoDbAttribute("jefe")
    public Profesor getJefe() {
        return jefe;
    }

    @DynamoDbAttribute("estudiantes")
    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }
}