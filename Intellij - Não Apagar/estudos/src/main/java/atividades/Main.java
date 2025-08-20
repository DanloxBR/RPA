package login;

import org.openqa.selenium.By;
import selenium.TelaHelper;

public class Login {

    public static void main(String[] args) throws InterruptedException {
        TelaHelper tela = new TelaHelper();

        try {
            tela.navegar("https://wise.com/br");

            By modal = By.xpath("//*[@id=\"transfer-section\"]/div/div/div[3]/div/div/div");
            tela.esperarVisibilidade(modal, 40);
            tela.scrollParaElemento(modal, 40);
            Thread.sleep(3000);

            selecionarMoeda(tela, "USD", true);
            selecionarMoeda(tela, "BRL", false);
            Thread.sleep(3000);

            By valorCambio = By.xpath("//button[@aria-describedby='rateLabel']");
            tela.esperarVisibilidade(valorCambio, 40);
            Thread.sleep(3000);

            String valor = tela.obterTexto(valorCambio, 40);
            if (valor != null && !valor.isEmpty()) {
                System.out.println("💱 Cotação atual: 1 USD = " + valor);
            } else if ((valor = tela.obterAtributo(valorCambio, "aria-label", 40)) != null && !valor.isEmpty()) {
                System.out.println("💱 Cotação atual (via aria-label): 1 USD = " + valor);
            } else {
                System.err.println("❌ Não foi possível obter o valor do câmbio.");
            }

        } finally {
            tela.finalizar();
        }
    }

    private static void selecionarMoeda(TelaHelper tela, String moeda, boolean origem) {
        By botaoBy = origem ?
                By.xpath("//button[@id='sourceSelectedCurrency']") :
                By.xpath("//button[@id='targetSelectedCurrency']");

        By inputBy = origem ?
                By.xpath("//input[@id='sourceSelectedCurrencySearch']") :
                By.xpath("//input[@id='targetSelectedCurrencySearch']");

        try {
            tela.abrirDropdownMoeda(botaoBy, 40);
            tela.digitarMoeda(inputBy, moeda, 40);
            tela.confirmarSelecaoMoeda(moeda, 40, botaoBy);
        } catch (InterruptedException e) {
            System.out.println("Erro ao digitar moeda: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
