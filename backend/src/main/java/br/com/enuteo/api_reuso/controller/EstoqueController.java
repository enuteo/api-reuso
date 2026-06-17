package br.com.enuteo.api_reuso.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enuteo.api_reuso.dto.pecas.Peca;
import br.com.enuteo.api_reuso.service.EstoqueService;

/**
 * Catálogo de peças (tabela `peca`: id, nome, preco).
 * O inventário/quantidade fica em InventarioController (/api/estoque).
 */
@RestController
@RequestMapping("/api/pecas")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public List<Peca> listar() {
        return estoqueService.listarPecas();
    }

    @GetMapping("/{id}")
    public Peca buscar(@PathVariable Long id) {
        return estoqueService.buscarPeca(id);
    }

    @PostMapping
    public Peca criar(@RequestBody Peca peca) {
        return estoqueService.criar(peca);
    }

    @PutMapping("/{id}")
    public Peca atualizar(@PathVariable Long id, @RequestBody Peca peca) {
        return estoqueService.atualizar(id, peca);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        estoqueService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
