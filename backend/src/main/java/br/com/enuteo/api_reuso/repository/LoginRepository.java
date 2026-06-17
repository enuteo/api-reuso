package br.com.enuteo.api_reuso.repository;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public LoginRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Busca o usuário pelo email. Retorna [id, nome, classe, senha] (senha = hash BCrypt),
     * ou null se não existir. A verificação da senha é feita no service (PasswordEncoder).
     */
    public Map<String, Object> findByEmail(String email) {
        String sql = "SELECT id, nome, classe, senha FROM usuario WHERE email = :email";
        try {
            return jdbcTemplate.queryForMap(sql, new MapSqlParameterSource("email", email));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Keywords = serviços acessíveis ao usuário.
     *  - Funcionário (classe 0): via acesso_funcionario -> servico
     *  - Cliente (classe 1):     via servico_cliente   -> servico
     */
    public List<String> keywords(Long usuarioId, int classe) {
        String sql = (classe == 0)
            ? "SELECT s.keyword FROM funcionario f "
              + "JOIN acesso_funcionario af ON af.funcionario_id = f.id "
              + "JOIN servico s ON s.id = af.servico_id "
              + "WHERE f.id_usuario = :uid"
            : "SELECT s.keyword FROM cliente c "
              + "JOIN servico_cliente sc ON sc.cliente_id = c.id "
              + "JOIN servico s ON s.id = sc.servico_id "
              + "WHERE c.id_usuario = :uid";

        return jdbcTemplate.queryForList(
            sql, new MapSqlParameterSource("uid", usuarioId), String.class);
    }
}
