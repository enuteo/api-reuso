package br.com.enuteo.api_reuso.dto.login;

import java.util.List;

public class LoginResponse {

    private Long id; // id do usuário (usado pelo front para buscar dados do cliente)
    private String nome;
    private int classe; // 0 = Funcionário | 1 = Cliente
    private List<String> keywords;

    public LoginResponse() {
    }

    public LoginResponse(Long id, String nome, int classe, List<String> keywords) {
        this.id = id;
        this.nome = nome;
        this.classe = classe;
        this.keywords = keywords;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getClasse() {
        return classe;
    }
    public void setClasse(int classe) {
        this.classe = classe;
    }
    public List<String> getKeywords() {
        return keywords;
    }
    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
