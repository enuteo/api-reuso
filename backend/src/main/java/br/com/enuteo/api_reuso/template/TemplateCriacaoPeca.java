package br.com.enuteo.api_reuso.template;

import java.util.Map;

import br.com.enuteo.api_reuso.dto.pecas.AbstractPeca;

public abstract class TemplateCriacaoPeca {

    public void criarNovaPeca(Map<String, Object> dadosPeca) {
        criarPecaNoBanco(dadosPeca);

    }

    abstract void criarPecaNoBanco(Map<String, Object> dadosPeca);
    abstract AbstractPeca mapearNovaPeca(Map<String, Object> dadosPeca);
    abstract void atualizarEstoque(AbstractPeca peca);

}
