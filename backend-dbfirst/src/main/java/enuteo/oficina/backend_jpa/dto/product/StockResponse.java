package enuteo.oficina.backend_jpa.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {
    private Integer id;
    private Integer pecaId;
    private String pecaNome;
    private Integer quantidade;
}
