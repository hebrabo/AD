package org.accesodatos.hogwarts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * PUNTO DE ENTRADA DE LA APLICACIÓN (MAIN)
 * ----------------------------------------
 * Es el único archivo que tenemos que ejecutar
 * para que todo el sistema (Base de Datos, Servidor Web, API) se ponga en marcha.
 */
@SpringBootApplication
// Esta sola palabra hace el trabajo de tres anotaciones juntas:
// 1. @Configuration: Permite configurar Spring.
// 2. @EnableAutoConfiguration: Spring mira las librerías (pom.xml) y se configura solo.
//    (Ej: "Veo que tienes el driver de Postgres, así que intentaré conectarme a una BD automáticamente").
// 3. @ComponentScan:
//    Le dice a Spring: "Escanea este paquete (org.accesodatos.hogwarts) y todos sus sub-paquetes".
//    Gracias a esto, Spring encuentra automáticamente las clases @Controller, @Service y @Repository.
public class HogwartsApplication {

    public static void main(String[] args) {

        SpringApplication.run(HogwartsApplication.class, args);
        // LA LÍNEA DE ARRANQUE:
        // Cuando esta línea se ejecuta, pasan 4 cosas:
        // 1. Inicia el "Contenedor de Spring" (la memoria donde viven los Servicios y Repositorios).
        // 2. Se conecta a la Base de Datos usando lo que hay en application.properties.
        // 3. Arranca un servidor web interno (Tomcat) en el puerto 8080.
        // 4. Despliega la API para poder consultarla desde el navegador o Postman.
    }

}
