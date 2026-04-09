package br.com.enuteo.api_reuso.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.enuteo.api_reuso.dto.pecas.AbstractPeca;
import br.com.enuteo.api_reuso.service.EstoqueService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api-reuso/pecas")
public class EstoqueController {

    private final EstoqueService estoqueService;
    @GetMapping
    public List<AbstractPeca> listar() {
        return new ArrayList<>();
    }

    @GetMapping("/{nome}/{id}")
    public AbstractPeca buscar(@PathVariable String nome, @PathVariable Long id) {
        return estoqueService.buscarPeca(id, nome);
    }

    @PostMapping
    public AbstractPeca criar(@RequestBody AbstractPeca peca) {
        return peca;
    }

    @PutMapping("/{id}")
    public AbstractPeca atualizar(@PathVariable Long id, @RequestBody AbstractPeca peca) {
        return peca;
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        // no need
    }
    
}
