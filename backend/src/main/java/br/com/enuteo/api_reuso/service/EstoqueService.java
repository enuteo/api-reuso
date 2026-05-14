package br.com.enuteo.api_reuso.service;

import java.util.List;
import org.springframework.stereotype.Service;

import br.com.enuteo.api_reuso.dto.pecas.AbstractPeca;
import br.com.enuteo.api_reuso.repository.EstoqueRepository;

// anotaçao service ja faz o singleton
@Service
public class EstoqueService {
    
    private final EstoqueRepository estoqueRepository;
    
    public EstoqueService(EstoqueRepository estoqueRepository){
        this.estoqueRepository = estoqueRepository;
    }

    // Novo método ponte para listar todas as peças
    public List<AbstractPeca> listarTodas() {
        return estoqueRepository.listarTodas();
    }

    public AbstractPeca buscarPeca(Long id, String nome){
        return estoqueRepository.buscarPeca(id, nome);
    }
}