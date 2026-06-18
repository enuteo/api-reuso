package br.com.enuteo.api_reuso.dto.usuario;

/** Resumo de usuário para a listagem da gestão (sem expor a senha). */
public class UsuarioResumo {

    private Long id;
    private String nome;
    private String email;
    private int classe; // 0 = Funcionário | 1 = Cliente

    public UsuarioResumo(Long id, String nome, String email, int classe) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.classe = classe;
    }

    public Long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public int getClasse() {
        return classe;
    }
}
