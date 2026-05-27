package enuteo.oficina.backend_jpa.service;

import enuteo.oficina.backend_jpa.dto.access.LoginRequest;
import enuteo.oficina.backend_jpa.dto.access.LoginResponse;
import enuteo.oficina.backend_jpa.entity.AcessoFuncionario;
import enuteo.oficina.backend_jpa.entity.Cliente;
import enuteo.oficina.backend_jpa.entity.Funcionario;
import enuteo.oficina.backend_jpa.entity.ServicoCliente;
import enuteo.oficina.backend_jpa.entity.Usuario;
import enuteo.oficina.backend_jpa.exception.InvalidCredentialsException;
import enuteo.oficina.backend_jpa.repository.AcessoFuncionarioRepository;
import enuteo.oficina.backend_jpa.repository.ClienteRepository;
import enuteo.oficina.backend_jpa.repository.FuncionarioRepository;
import enuteo.oficina.backend_jpa.repository.ServicoClienteRepository;
import enuteo.oficina.backend_jpa.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ClienteRepository clienteRepository;
    private final AcessoFuncionarioRepository acessoFuncionarioRepository;
    private final ServicoClienteRepository servicoClienteRepository;

    public AuthServiceImpl(UsuarioRepository usuarioRepository,
                           FuncionarioRepository funcionarioRepository,
                           ClienteRepository clienteRepository,
                           AcessoFuncionarioRepository acessoFuncionarioRepository,
                           ServicoClienteRepository servicoClienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.clienteRepository = clienteRepository;
        this.acessoFuncionarioRepository = acessoFuncionarioRepository;
        this.servicoClienteRepository = servicoClienteRepository;
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        Usuario usuario = usuarioRepository.findByNomeAndSenha(req.getNome(), req.getSenha())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        List<String> keywords;
        if (usuario.getClasse() != null && usuario.getClasse() == 0) {
            Funcionario func = funcionarioRepository.findByUsuario_Id(usuario.getId()).orElse(null);
            if (func == null) {
                keywords = Collections.emptyList();
            } else {
                List<AcessoFuncionario> acessos = acessoFuncionarioRepository.findByFuncionario_Id(func.getId());
                keywords = acessos.stream()
                        .map(a -> a.getServico().getKeyword())
                        .collect(Collectors.toList());
            }
        } else {
            Cliente cliente = clienteRepository.findByUsuario_Id(usuario.getId()).orElse(null);
            if (cliente == null) {
                keywords = Collections.emptyList();
            } else {
                List<ServicoCliente> rel = servicoClienteRepository.findByCliente_Id(cliente.getId());
                keywords = rel.stream()
                        .map(r -> r.getServico().getKeyword())
                        .collect(Collectors.toList());
            }
        }

        return new LoginResponse(usuario.getNome(), usuario.getClasse(), keywords);
    }
}
