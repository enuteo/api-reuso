package br.com.enuteo.api_reuso.service;

import org.springframework.stereotype.Service;

import br.com.enuteo.api_reuso.dto.pecas.AbstractPeca;
import br.com.enuteo.api_reuso.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;

// anotaçao service ja faz o singleton
@Service
public class EstoqueService {
    
    private final EstoqueRepository estoqueRepository;
    
    public EstoqueService(EstoqueRepository estoqueRepository){
        this.estoqueRepository = estoqueRepository;
    }
    public AbstractPeca buscarPeca(Long id, String nome){
        return estoqueRepository.buscarPeca(id, nome);
    }

}
