package enuteo.oficina.backend_jpa.repository;

import enuteo.oficina.backend_jpa.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Optional<Usuario> findByNomeAndSenha(String nome, String senha);
}
