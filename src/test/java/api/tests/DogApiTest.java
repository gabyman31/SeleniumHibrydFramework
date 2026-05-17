package api.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.endpoints.DogEndpoints;
import io.restassured.response.Response;
import java.util.List;

public class DogApiTest {

    @Test
    public void validarColeccionDeRazas() {
        // 1. Ejecutar petición GET al recurso /breeds
        Response respuesta = DogEndpoints.obtenerRazas();
        
        System.out.println("--- RESPUESTA DOG API: LISTA DE RAZAS ---");
        respuesta.prettyPrint(); // Despliega la estructura JSON:API en consola
        
        // 2. Aserciones base
        Assert.assertEquals(respuesta.getStatusCode(), 200, "El código de estado no es 200");
        
        // 3. Validar que la lista de datos contenga elementos (no esté vacía)
        List<Object> listaRazas = respuesta.jsonPath().getList("data");
        Assert.assertFalse(listaRazas.isEmpty(), "La lista de razas de perros regresó vacía");
        
        // 4. Validar el tipo de recurso del primer elemento según la convención JSON:API
        String tipoRecurso = respuesta.jsonPath().getString("data[0].type");
        Assert.assertEquals(tipoRecurso, "breed", "El tipo de objeto en el nodo no corresponde a 'breed'");
    }

    @Test
    public void validarRazaEspecificaPorId() {
        // ID único (UUID) correspondiente a una raza registrada en el sistema de Dog API
        String idRazaEjemplo = "68f47c5a-5115-47cd-9849-e45d3c378f12"; 
        
        Response respuesta = DogEndpoints.obtenerRazaPorId(idRazaEjemplo);
        
        System.out.println("--- RESPUESTA DOG API: CONSULTA POR ID ---");
        respuesta.prettyPrint();
        
        Assert.assertEquals(respuesta.getStatusCode(), 200);
        
        // Validación del nombre de la raza dentro de la jerarquía de atributos
        String nombreRaza = respuesta.jsonPath().getString("data.attributes.name");
        Assert.assertNotNull(nombreRaza, "El nombre de la raza no pudo ser leído del JSON");
        System.out.println("Raza identificada con éxito: " + nombreRaza);
    }

    @Test
    public void validarDatosCuriososAleatorios() {
        // 1. Consumir el servicio de "Facts"
        Response respuesta = DogEndpoints.obtenerDatosCuriosos();
        
        System.out.println("--- RESPUESTA DOG API: DATOS CURIOSOS ---");
        respuesta.prettyPrint();
        
        Assert.assertEquals(respuesta.getStatusCode(), 200);
        
        // 2. Comprobar que el texto de la curiosidad contenga caracteres y no sea nulo
        String hechoCurioso = respuesta.jsonPath().getString("data[0].attributes.body");
        Assert.assertTrue(hechoCurioso.length() > 10, "El dato curioso devuelto es demasiado corto o inválido");
    }
}
