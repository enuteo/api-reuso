package br.com.enuteo.api_reuso.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
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

@RestController
@RequestMapping("/api/pecas") 
@CrossOrigin(origins = "*")   
public class EstoqueController {

    private final EstoqueService estoqueService;
    
    public EstoqueController(EstoqueService estoqueService){
        this.estoqueService = estoqueService;
    }
    
    @GetMapping
    public List<AbstractPeca> listar() {
        return estoqueService.listarTodas();
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
        // No operation required for current MVP scope
    }
}