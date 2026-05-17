package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GooglePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // 1. Localizadores
    private By cajaBusqueda = By.name("q");
    // Selecciona el primer encabezado de resultado H3 que esté dentro de un enlace principal
    private By primerResultado = By.xpath("(//h3)[1]");

    // 2. Constructor
    public GooglePage(WebDriver driver) {
        this.driver = driver;
        // Inicializa una espera explícita de 10 segundos
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 3. Acciones
    public void ingresarUrl() {
        driver.get("https://google.com");
    }

    public void buscarTexto(String texto) {
        wait.until(ExpectedConditions.elementToBeClickable(cajaBusqueda)).sendKeys(texto);
        driver.findElement(cajaBusqueda).submit();
    }

    public void hacerClicEnPrimerResultado() {
        // Espera a que el primer enlace sea visible y clickeable antes de interactuar
        wait.until(ExpectedConditions.elementToBeClickable(primerResultado)).click();
    }

    public String obtenerTitulo() {
        return driver.getTitle();
    }
}
