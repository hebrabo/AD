package modelos;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@NoArgsConstructor
@DynamoDbBean
public class ReproduccionDynamo {

    private String contenido;
    private String fecha;

    @DynamoDbAttribute("contenido")
    public String getContenido() {
        return contenido;
    }

    @DynamoDbAttribute("fecha")
    public String getFecha() {
        return fecha;
    }
}