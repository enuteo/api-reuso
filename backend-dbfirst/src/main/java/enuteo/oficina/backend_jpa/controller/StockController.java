package enuteo.oficina.backend_jpa.controller;

import enuteo.oficina.backend_jpa.dto.product.StockRequest;
import enuteo.oficina.backend_jpa.dto.product.StockResponse;
import enuteo.oficina.backend_jpa.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<StockResponse>> listAll() {
        return ResponseEntity.ok(stockService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(stockService.getById(id));
    }

    @PostMapping
    public ResponseEntity<StockResponse> create(@RequestBody StockRequest req) {
        return ResponseEntity.ok(stockService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockResponse> updateQuantity(@PathVariable Integer id, @RequestBody StockRequest req) {
        return ResponseEntity.ok(stockService.updateQuantity(id, req.getQuantidade()));
    }

    @PatchMapping("/{id}/adjust")
    public ResponseEntity<StockResponse> adjustQuantity(@PathVariable Integer id, @RequestBody Integer delta) {
        return ResponseEntity.ok(stockService.adjustQuantity(id, delta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        stockService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
