package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;
import tests.BaseTest;

public class ErrorHandling implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("La prueba falló: " + result.getName() + ". Tomando evidencia...");
        // Instancia BaseTest para invocar la captura usando el driver de la sesión actual
        BaseTest base = new BaseTest();
        base.capturarPantalla(result.getName());
    }
}
