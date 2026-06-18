package br.com.enuteo.api_reuso.dto.cliente;

/**
 * Atualização de perfil do cliente. `currentPassword`/`newPassword` são opcionais
 * (só preencher para trocar a senha).
 */
public class AtualizarPerfilRequest {

    private String nome;
    private String telefone;
    private String email;
    private String currentPassword;
    private String newPassword;

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
    public String getCurrentPassword() {
        return currentPassword;
    }
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
