package modelos;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;
import java.util.List;

@Data
@NoArgsConstructor
@DynamoDbBean
public class HistorialDynamo {

    private int idUsuario;
    private String email;
    private List<ReproduccionDynamo> reproducciones;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("idUsuario")
    public int getIdUsuario() {
        return idUsuario;
    }

    @DynamoDbAttribute("email")
    public String getEmail() {
        return email;
    }

    @DynamoDbAttribute("reproducciones")
    public List<ReproduccionDynamo> getReproducciones() {
        return reproducciones;
    }
}