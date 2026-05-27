package enuteo.oficina.backend_jpa.repository;

import enuteo.oficina.backend_jpa.entity.ServicoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoClienteRepository extends JpaRepository<ServicoCliente, Integer> {
	List<ServicoCliente> findByCliente_Id(Integer clienteId);
}
