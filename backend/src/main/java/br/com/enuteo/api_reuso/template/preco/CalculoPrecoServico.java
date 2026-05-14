package br.com.enuteo.api_reuso.template.preco;

public class CalculoPrecoServico extends TemplateCalculoPreco {

    @Override
    protected double obterPrecoBase() {
        System.out.println("[Template 3] Valor base da mão de obra: R$ 150.00");
        return 150.00;
    }

    @Override
    protected double calcularImpostos(double precoBase) {
        System.out.println("[Template 3] Calculando 10% de taxa sobre o serviço...");
        return precoBase * 0.10; 
    }
}