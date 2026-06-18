package br.com.enuteo.api_reuso.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enuteo.api_reuso.dto.estoque.EstoqueItem;
import br.com.enuteo.api_reuso.service.EstoqueService;

/**
 * Inventário: quantidades em estoque (tabela `estoque` JOIN `peca`).
 */
@RestController
@RequestMapping("/api/estoque")
public class InventarioController {

    private final EstoqueService estoqueService;

    public InventarioController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public List<EstoqueItem> listar() {
        System.out.println("listar estoque");
        return estoqueService.listarEstoque();
    }
}
