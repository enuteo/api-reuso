package br.com.enuteo.api_reuso.dto.estoque;

/**
 * Item de inventário: junção de `estoque` com `peca`.
 * Espelha o contrato GET /api/estoque.
 */
public class EstoqueItem {

    private Long id;
    private Long pecaId;
    private String pecaNome;
    private int quantidade;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getPecaId() {
        return pecaId;
    }
    public void setPecaId(Long pecaId) {
        this.pecaId = pecaId;
    }
    public String getPecaNome() {
        return pecaNome;
    }
    public void setPecaNome(String pecaNome) {
        this.pecaNome = pecaNome;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
