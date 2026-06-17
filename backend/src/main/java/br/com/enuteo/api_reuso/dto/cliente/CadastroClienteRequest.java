package br.com.enuteo.api_reuso.dto.cliente;

/**
 * Body do cadastro de cliente (POST /api/clientes).
 * Gera um `usuario` (classe = 1) + um `cliente` vinculado.
 */
public class CadastroClienteRequest {

    private String nome;
    private String telefone;
    private String email;
    private String senha;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
