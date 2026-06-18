package br.com.enuteo.api_reuso.dto.cliente;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.enuteo.api_reuso.dto.usuario.CadastroClienteResponse;
import br.com.enuteo.api_reuso.dto.usuario.CadastroUsuarioRequest;
import br.com.enuteo.api_reuso.dto.usuario.UsuarioResumo;
import br.com.enuteo.api_reuso.exception.InvalidUser;
import br.com.enuteo.api_reuso.repository.ClienteRepository;
import br.com.enuteo.api_reuso.repository.UsuarioRepository;

/**
 * Cadastro de usuário pela tela de gestão (master).
 * Cria o `usuario` (credencial, senha BCrypt) e o perfil correspondente:
 *  - classe 1 (cliente)     -> tabela `cliente`
 *  - classe 0 (funcionário) -> tabela `funcionario` (exige `cargo`)
 */
@Service
public class UsuarioService {

    private static final int CLASSE_CLIENTE = 1;

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(ClienteRepository clienteRepository,
                          UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CadastroClienteResponse cadastraUsuario(CadastroUsuarioRequest request) {
        
        if (clienteRepository.existeEmail(request.getEmail())) {
            System.out.println("Cadastro de email ja existente");
            throw new InvalidUser("Já existe uma conta com esse e-mail.");
        }

        int classe = request.getClasse() != null ? request.getClasse() : CLASSE_CLIENTE;
        String senhaHash = passwordEncoder.encode(request.getSenha());
        Long usuarioId = clienteRepository.inserirUsuario(
            request.getNome(), request.getEmail(), senhaHash, classe);

        System.out.println("usuario inserido: " + usuarioId);

        if (classe == CLASSE_CLIENTE) {

            System.out.println("inserindo cliente");
            clienteRepository.inserirCliente(request.getNome(), request.getEmail(), null, usuarioId);
        } else {
            System.out.println("inserindo funcionario");
            usuarioRepository.inserirFuncionario(request.getNome(), "cargo", usuarioId);
        }

        CadastroClienteResponse res = new CadastroClienteResponse();
        res.setMensagem(classe == CLASSE_CLIENTE
            ? "Cliente cadastrado com sucesso."
            : "Funcionário cadastrado com sucesso.");

        System.out.println(res.getMensagem());
        return res;
    }

    public List<UsuarioResumo> listar() {
        return usuarioRepository.listar();
    }

    @Transactional
    public void deletar(Long usuarioId) {
        usuarioRepository.deletarFisico(usuarioId);
    }
}
