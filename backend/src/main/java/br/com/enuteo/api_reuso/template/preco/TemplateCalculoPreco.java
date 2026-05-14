package br.com.enuteo.api_reuso.template.preco;

public abstract class TemplateCalculoPreco {
    
    // O Template Method propriamente dito
    public final double calcularTotal() {
        double base = obterPrecoBase();
        return base + calcularImpostos(base);
    }

    protected abstract double obterPrecoBase();
    protected abstract double calcularImpostos(double precoBase);
}