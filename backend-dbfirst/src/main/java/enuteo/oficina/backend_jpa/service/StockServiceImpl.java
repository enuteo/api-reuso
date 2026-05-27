package enuteo.oficina.backend_jpa.service;

import enuteo.oficina.backend_jpa.dto.product.StockRequest;
import enuteo.oficina.backend_jpa.dto.product.StockResponse;
import enuteo.oficina.backend_jpa.entity.Estoque;
import enuteo.oficina.backend_jpa.entity.Peca;
import enuteo.oficina.backend_jpa.exception.ResourceNotFoundException;
import enuteo.oficina.backend_jpa.repository.EstoqueRepository;
import enuteo.oficina.backend_jpa.repository.PecaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    private final EstoqueRepository estoqueRepository;
    private final PecaRepository pecaRepository;

    public StockServiceImpl(EstoqueRepository estoqueRepository, PecaRepository pecaRepository) {
        this.estoqueRepository = estoqueRepository;
        this.pecaRepository = pecaRepository;
    }

    @Override
    public List<StockResponse> listAll() {
        return estoqueRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StockResponse getById(Integer id) {
        Estoque e = estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque not found: " + id));
        return toDto(e);
    }

    @Override
    public StockResponse create(StockRequest req) {
        Peca peca = pecaRepository.findById(req.getPecaId())
                .orElseThrow(() -> new ResourceNotFoundException("Peca not found: " + req.getPecaId()));
        Estoque e = new Estoque();
        e.setPeca(peca);
        e.setQuantidade(req.getQuantidade());
        Estoque saved = estoqueRepository.save(e);
        return toDto(saved);
    }

    @Override
    public StockResponse updateQuantity(Integer id, Integer quantidade) {
        Estoque e = estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque not found: " + id));
        e.setQuantidade(quantidade);
        Estoque saved = estoqueRepository.save(e);
        return toDto(saved);
    }

    @Override
    public StockResponse adjustQuantity(Integer id, Integer delta) {
        Estoque e = estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque not found: " + id));
        int newQty = e.getQuantidade() + delta;
        if (newQty < 0) {
            throw new IllegalArgumentException("Resulting quantity cannot be negative");
        }
        e.setQuantidade(newQty);
        Estoque saved = estoqueRepository.save(e);
        return toDto(saved);
    }

    @Override
    public void delete(Integer id) {
        Estoque e = estoqueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estoque not found: " + id));
        estoqueRepository.delete(e);
    }

    private StockResponse toDto(Estoque e) {
        Peca p = e.getPeca();
        return new StockResponse(e.getId(), p != null ? p.getId() : null, p != null ? p.getNome() : null, e.getQuantidade());
    }
}
