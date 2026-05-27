package enuteo.oficina.backend_jpa.service;

import enuteo.oficina.backend_jpa.dto.product.StockRequest;
import enuteo.oficina.backend_jpa.dto.product.StockResponse;

import java.util.List;

public interface StockService {
    List<StockResponse> listAll();
    StockResponse getById(Integer id);
    StockResponse create(StockRequest req);
    StockResponse updateQuantity(Integer id, Integer quantidade);
    StockResponse adjustQuantity(Integer id, Integer delta);
    void delete(Integer id);
}
