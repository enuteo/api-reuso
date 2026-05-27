package enuteo.oficina.backend_jpa.repository;

import enuteo.oficina.backend_jpa.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	Optional<Cliente> findByUsuario_Id(Integer usuarioId);
}
