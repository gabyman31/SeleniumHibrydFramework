package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GooglePage;

public class GoogleTest extends BaseTest {
	 @Test(enabled = false, description = "Prueba de interfaz de Google deshabilitada temporalmente")
    public void validarBusquedaYClickGoogle() {
        GooglePage googlePage = new GooglePage(driver);
        
        googlePage.ingresarUrl();
        googlePage.buscarTexto("Selenium Java");
        
        // Hace clic en el primer enlace devuelto por Google
        googlePage.hacerClicEnPrimerResultado();
        
        // Captura el título de la nueva página cargada
        String tituloActual = googlePage.obtenerTitulo();
        System.out.println("Título de la página destino: " + tituloActual);
        
        // Validación: Verifica que el título contenga la palabra clave esperada
        Assert.assertTrue(tituloActual.toLowerCase().contains("selenium"), 
                "El título de la página destino no contiene la palabra esperada. Encontrado: " + tituloActual);
    }

}
