package selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class TelaHelper {

    private static final WebDriver driver;

    static {
        driver = DriverFactory.getDriver();
    }
  
    private TelaHelper() {}

    public static WebDriver getDriver() {
        return driver;
    }
    public static void navegar(String url) {
        driver.get(url);
    }
    public static void finalizar() {
        if (driver != null) {
            driver.quit();
        }
    }
    public static WebDriverWait getWait(int timeoutSegundos) {
        return new WebDriverWait(driver, timeoutSegundos);
    }
    public static JavascriptExecutor getJsExecutor() {
        return (JavascriptExecutor) driver;
    }
    public static void scrollParaElemento(By by, int timeoutSegundos) {
        WebElement elemento = esperarVisibilidade(by, timeoutSegundos);
        getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", elemento);
    }
    public static void clicar(By by, int timeoutSegundos) {
        WebDriverWait wait = getWait(timeoutSegundos);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
            scrollParaElemento(by, timeoutSegundos);
            WebElement elemento = driver.findElement(by);
            elemento.click();
        } catch (ElementClickInterceptedException e) {
            clicarViaJS(by);
        }
    }
    public static void clicarViaJS(By by) {
        WebElement elemento = driver.findElement(by);
        getJsExecutor().executeScript("arguments[0].click();", elemento);
    }
    public static void preencherCampo(By by, String texto, int timeoutSegundos) {
        WebElement campo = getWait(timeoutSegundos)
                .until(ExpectedConditions.visibilityOfElementLocated(by));
        campo.clear();
        campo.sendKeys(texto);
    }
    public static WebElement esperarVisibilidade(By by, int timeoutSegundos) {
        return getWait(timeoutSegundos)
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    public static List<WebElement> esperarVisibilidadeLista(By by, int timeoutSegundos) {
        return getWait(timeoutSegundos)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }
    public static String obterTexto(By by, int timeoutSegundos) {
        try {
            return esperarVisibilidade(by, timeoutSegundos).getText();
        } catch (Exception e) {
            return null;
        }
    }
    public static String obterAtributo(By by, String atributo, int timeoutSegundos) {
        try {
            return esperarVisibilidade(by, timeoutSegundos).getAttribute(atributo);
        } catch (Exception e) {
            return null;
        }
    }
    public static void abrirDropdownMoeda(By botaoBy, int timeoutSegundos) {
        try {
            clicar(botaoBy, timeoutSegundos);
        } catch (Exception ex) {
            clicarViaJS(botaoBy);
        }
    }
    public static void digitarMoeda(By inputBy, String moeda, int timeoutSegundos) throws InterruptedException {
        preencherCampo(inputBy, moeda, timeoutSegundos);
        Thread.sleep(500);
    }
    public static void confirmarSelecaoMoeda(String moeda, int timeoutSegundos, By botaoConfirmacaoBy) {
        By itemListaBy = By.xpath(
                "//div[@role='listbox']//div[contains(text(), '" + moeda + "') " +
                        "and not(ancestor-or-self::*[contains(@style,'display:none') or contains(@style,'visibility:hidden')])]");
        try {
            clicar(itemListaBy, timeoutSegundos);
        } catch (Exception ex) {
            clicarViaJS(itemListaBy);
        }
        WebElement botao = esperarVisibilidade(botaoConfirmacaoBy, timeoutSegundos);
        String texto = botao.getText();
        if (texto == null || texto.trim().isEmpty()) {
            texto = botao.getAttribute("aria-label");
        }
        System.out.println("Moeda refletida no botão: " + texto);

        if (texto == null || !texto.toUpperCase().contains(moeda.toUpperCase())) {
            System.err.println("❌ A moeda '" + moeda + "' não foi refletida corretamente.");
        }
    }
    public static List<WebElement> encontrarElementos(By by) {
        return driver.findElements(by);
    }
}
