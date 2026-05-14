package br.com.enuteo.api_reuso.template.relatorio;

public class RelatorioSimples extends TemplateRelatorio {

    @Override
    protected void coletarDados() {
        System.out.println("[Template 2] Consultando tabela de Serviços...");
    }

    @Override
    protected void exportarArquivo() {
        System.out.println("[Template 2] Arquivo TXT gerado com sucesso.");
    }
}