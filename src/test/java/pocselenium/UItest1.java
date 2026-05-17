package pocselenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class UItest1 {

	public static void main(String[] args) {
		// Configura automáticamente el driver de Chrome
        WebDriverManager.chromedriver().setup();
        
        // Crea una instancia del navegador Chrome
        WebDriver driver = new ChromeDriver();
        
        //Maximiza la ventana del navegador
        driver.manage().window().maximize();
        
        // Navega a la página de Google
        driver.get("https://www.google.com");
        
        //Imprime el título de la página en la consola
        System.out.println("El título de la página es: " + driver.getTitle());
        
        // Cierra el navegador
        driver.quit();

	}

}
