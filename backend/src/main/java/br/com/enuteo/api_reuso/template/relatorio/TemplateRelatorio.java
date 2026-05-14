package br.com.enuteo.api_reuso.template.relatorio;

public abstract class TemplateRelatorio {
    
    // O Template Method propriamente dito
    public final void gerarRelatorio() {
        coletarDados();
        exportarArquivo();
    }

    protected abstract void coletarDados();
    protected abstract void exportarArquivo();
}