package enuteo.oficina.backend_jpa.dto.product;

import lombok.Data;

@Data
public class StockRequest {
    private Integer pecaId;
    private Integer quantidade;
}
