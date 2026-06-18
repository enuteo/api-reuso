package br.com.enuteo.api_reuso.dto.usuario;

public class CadastroUsuarioRequest {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Integer classe; // 0 = Funcionário | 1 = Cliente
    private String cargo;   // usado quando classe = 0 (funcionário)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
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
    public Integer getClasse() {
        return classe;
    }
    public void setClasse(Integer classe) {
        this.classe = classe;
    }
    
}
