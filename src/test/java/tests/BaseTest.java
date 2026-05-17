package tests;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;

public class BaseTest {
    // Cambiamos a static para que el Listener pueda acceder al driver activo
    public static WebDriver driver; 

    @Parameters("navegador")
    @BeforeMethod
    public void setUp(String navegador) {
        // Detecta automáticamente si corre en GitHub Actions (devuelve true o false)
        boolean esCI = System.getenv("GITHUB_ACTIONS") != null;

        if (navegador.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            
            if (esCI) {
                options.addArguments("--headless=new"); 
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
            options.setExperimentalOption("useAutomationExtension", false);
            
            driver = new ChromeDriver(options);
            
            if (driver instanceof ChromeDriver) {
                ((ChromeDriver) driver).executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", 
                    Collections.singletonMap("source", "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"));
            }
            
        } else if (navegador.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (esCI) {
                options.addArguments("-headless");
            }
            driver = new FirefoxDriver(options);
            
        } else if (navegador.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            if (esCI) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
            }
            driver = new EdgeDriver(options);
        }
        
        if (driver != null) {
            driver.manage().window().maximize();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Método auxiliar que ejecutará el Listener para guardar la imagen
    public void capturarPantalla(String nombreMetodo) {
        if (driver != null) {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // Crea una carpeta llamada 'screenshots' en la raíz del proyecto
            File destFile = new File("./screenshots/" + nombreMetodo + ".png");
            try {
                destFile.getParentFile().mkdirs();
                Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Captura de pantalla guardada en: " + destFile.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Fallo al guardar la captura: " + e.getMessage());
            }
        }
    }
}

