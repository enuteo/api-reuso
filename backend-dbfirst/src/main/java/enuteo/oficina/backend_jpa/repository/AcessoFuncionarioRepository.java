package enuteo.oficina.backend_jpa.repository;

import enuteo.oficina.backend_jpa.entity.AcessoFuncionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcessoFuncionarioRepository extends JpaRepository<AcessoFuncionario, Integer> {
	List<AcessoFuncionario> findByFuncionario_Id(Integer funcionarioId);
}
