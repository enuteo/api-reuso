package br.com.enuteo.api_reuso.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.enuteo.api_reuso.dto.estoque.EstoqueItem;
import br.com.enuteo.api_reuso.dto.pecas.Peca;
import br.com.enuteo.api_reuso.repository.EstoqueRepository;

// anotaçao service ja faz o singleton
@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public List<Peca> listarPecas() {
        return estoqueRepository.listarPecas();
    }

    public Peca buscarPeca(Long id) {
        return estoqueRepository.buscarPeca(id);
    }

    public Peca criar(Peca peca) {
        return estoqueRepository.inserir(peca);
    }

    public Peca atualizar(Long id, Peca peca) {
        estoqueRepository.atualizar(id, peca);
        peca.setId(id);
        return peca;
    }

    // Exclusão em cascata: remove o estoque vinculado antes da peça (atômico).
    public void deletar(Long id) {
        estoqueRepository.deletarEstoquePorPeca(id);
        estoqueRepository.deletar(id);
    }

    public List<EstoqueItem> listarEstoque() {
        return estoqueRepository.listarEstoque();
    }
}
