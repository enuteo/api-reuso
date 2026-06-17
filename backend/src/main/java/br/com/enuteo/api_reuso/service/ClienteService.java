package br.com.enuteo.api_reuso.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.enuteo.api_reuso.dto.cliente.CadastroClienteRequest;
import br.com.enuteo.api_reuso.dto.cliente.ClienteResponse;
import br.com.enuteo.api_reuso.exception.InvalidUser;
import br.com.enuteo.api_reuso.repository.ClienteRepository;

@Service
public class ClienteService {

    private static final int CLASSE_CLIENTE = 1; // 0 = Funcionário | 1 = Cliente

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Cria usuário (classe 1) + cliente vinculado, de forma atômica.
     * A senha é gravada com hash BCrypt. O login será feito por `email` + `senha`.
     */
    @Transactional
    public ClienteResponse cadastrar(CadastroClienteRequest req) {
        if (clienteRepository.existeEmail(req.getEmail())) {
            throw new InvalidUser("Já existe uma conta com esse e-mail.");
        }

        String senhaHash = passwordEncoder.encode(req.getSenha());
        Long usuarioId = clienteRepository.inserirUsuario(
            req.getNome(), req.getEmail(), senhaHash, CLASSE_CLIENTE);
        Long clienteId = clienteRepository.inserirCliente(
            req.getNome(), req.getEmail(), req.getTelefone(), usuarioId);

        return new ClienteResponse(clienteId, req.getNome(), req.getEmail(), req.getTelefone());
    }
}
