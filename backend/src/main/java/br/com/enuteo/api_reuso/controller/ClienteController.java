package br.com.enuteo.api_reuso.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enuteo.api_reuso.dto.cliente.CadastroClienteRequest;
import br.com.enuteo.api_reuso.dto.cliente.ClienteResponse;
import br.com.enuteo.api_reuso.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> cadastrar(@RequestBody CadastroClienteRequest request) {
        ClienteResponse cliente = clienteService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }
}
