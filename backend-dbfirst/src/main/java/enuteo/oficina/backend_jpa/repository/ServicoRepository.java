package enuteo.oficina.backend_jpa.repository;

import enuteo.oficina.backend_jpa.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {
}
