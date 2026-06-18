package br.com.enuteo.api_reuso.dto.servico;

import java.math.BigDecimal;

/** Serviço vinculado a um cliente (tabela `servico`). */
public class ServicoResponse {

    private Long id;
    private String descricao;
    private BigDecimal preco;
    private String keyword;

    public ServicoResponse(Long id, String descricao, BigDecimal preco, String keyword) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }
    public String getDescricao() {
        return descricao;
    }
    public BigDecimal getPreco() {
        return preco;
    }
    public String getKeyword() {
        return keyword;
    }
}
