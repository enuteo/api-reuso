package br.com.enuteo.api_reuso.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enuteo.api_reuso.dto.cliente.AtualizarPerfilRequest;
import br.com.enuteo.api_reuso.dto.cliente.CadastroClienteRequest;
import br.com.enuteo.api_reuso.dto.cliente.ClienteResponse;
import br.com.enuteo.api_reuso.dto.cliente.UsuarioService;
import br.com.enuteo.api_reuso.dto.servico.ServicoResponse;
import br.com.enuteo.api_reuso.dto.usuario.CadastroClienteResponse;
import br.com.enuteo.api_reuso.dto.usuario.CadastroUsuarioRequest;
import br.com.enuteo.api_reuso.dto.usuario.UsuarioResumo;
import br.com.enuteo.api_reuso.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final UsuarioService usuarioService;

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService, UsuarioService usuarioService) {
        this.clienteService = clienteService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@RequestBody CadastroClienteRequest request) {
        System.out.println("cadastro de cliente");
        ClienteResponse cliente = clienteService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    // Perfil do cliente logado (identificado pelo id do usuário guardado na sessão do front).
    @GetMapping("/usuario/{usuarioId}")
    public ClienteResponse perfil(@PathVariable Long usuarioId) {
        System.out.println("buscar perfil");
        return clienteService.buscarPorUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/servicos")
    public List<ServicoResponse> servicos(@PathVariable Long usuarioId) {
        System.out.println("servicos cliente");
        return clienteService.listarServicos(usuarioId);
    }

    @PutMapping("/usuario/{usuarioId}")
    public ClienteResponse atualizarPerfil(
            @PathVariable Long usuarioId, @RequestBody AtualizarPerfilRequest request) {
        System.out.println("atualizar perfil");
        return clienteService.atualizarPerfil(usuarioId, request);
    }

    @PostMapping("/usuario/cadastro")
    public CadastroClienteResponse cadastro(@RequestBody CadastroUsuarioRequest request){
        return usuarioService.cadastraUsuario(request);
    }

    // Lista de usuários do sistema (limite de 25).
    @GetMapping("/usuario")
    public List<UsuarioResumo> listarUsuarios() {
        return usuarioService.listar();
    }

    // Exclusão física do usuário (cascata nas tabelas dependentes).
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> removerUsuario(@PathVariable Long usuarioId) {
        usuarioService.deletar(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
