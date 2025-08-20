package login;

import org.openqa.selenium.By;
import selenium.TelaHelper;

public class Login {

    public static void main(String[] args) throws InterruptedException {

        try {
            TelaHelper.navegar("https://wise.com/br");

            By modal = By.xpath("//*[@id=\"transfer-section\"]/div/div/div[3]/div/div/div");
            TelaHelper.esperarVisibilidade(modal, 40);
            TelaHelper.scrollParaElemento(modal, 40);
            Thread.sleep(3000);

            selecionarMoeda("USD", true);
            selecionarMoeda("BRL", false);
            Thread.sleep(3000);

            By valorCambio = By.xpath("//button[@aria-describedby='rateLabel']");
            TelaHelper.esperarVisibilidade(valorCambio, 40);
            Thread.sleep(3000);

            String valor = TelaHelper.obterTexto(valorCambio, 40);
            if (valor != null && !valor.isEmpty()) {
                System.out.println("💱 Cotação atual: 1 USD = " + valor);
            } else if ((valor = TelaHelper.obterAtributo(valorCambio, "aria-label", 40)) != null && !valor.isEmpty()) {
                System.out.println("💱 Cotação atual (via aria-label): 1 USD = " + valor);
            } else {
                System.out.println("❌ Não foi possível obter o valor do câmbio.");
            }

        } finally {
            TelaHelper.finalizar();
        }
    }
    private static void selecionarMoeda(String moeda, boolean origem) {
        By botao = origem ?
                By.xpath("//button[@id='sourceSelectedCurrency']") :
                By.xpath("//button[@id='targetSelectedCurrency']");

        By input = origem ?
                By.xpath("//input[@id='sourceSelectedCurrencySearch']") :
                By.xpath("//input[@id='targetSelectedCurrencySearch']");

        try {
            TelaHelper.abrirDropdownMoeda(botao, 40);
            TelaHelper.digitarMoeda(input, moeda, 40);
            TelaHelper.confirmarSelecaoMoeda(moeda, 40, botao);
        } catch (InterruptedException e) {
            System.out.println("Erro ao digitar moeda: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
