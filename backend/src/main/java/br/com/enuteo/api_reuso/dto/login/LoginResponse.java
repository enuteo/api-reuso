package br.com.enuteo.api_reuso.dto.login;

import java.util.List;

public class LoginResponse {

    private String nome;
    private int classe; // 0 = Funcionário | 1 = Cliente
    private List<String> keywords;

    public LoginResponse() {
    }

    public LoginResponse(String nome, int classe, List<String> keywords) {
        this.nome = nome;
        this.classe = classe;
        this.keywords = keywords;
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
