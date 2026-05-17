package api.endpoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DogEndpoints {
    
    // Base URL oficial de Dog API V2
    private static final String BASE_URL = "https://dogapi.dog/api/v2";

    // Método HTTP GET: Obtiene la colección completa de razas de perros
    public static Response obtenerRazas() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .when()
                .get("/breeds");
    }

    // Método HTTP GET: Obtiene una raza en específico mediante su ID único (UUID)
    public static Response obtenerRazaPorId(String id) {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .get("/breeds/{id}");
    }

    // Método HTTP GET: Obtiene datos curiosos e informativos aleatorios (Dog Facts)
    public static Response obtenerDatosCuriosos() {
        return RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .when()
                .get("/facts");
    }
}
