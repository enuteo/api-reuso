package enuteo.oficina.backend_jpa.repository;

import enuteo.oficina.backend_jpa.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {
}
