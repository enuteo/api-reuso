package br.com.enuteo.api_reuso.repository;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ClienteRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public boolean existeEmail(String email) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM usuario WHERE email = :email",
            new MapSqlParameterSource("email", email),
            Integer.class);
        return count != null && count > 0;
    }

    public Long inserirUsuario(String nome, String email, String senha, int classe) {
        String sql = "INSERT INTO usuario (nome, email, senha, classe) VALUES (:nome, :email, :senha, :classe)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource()
            .addValue("nome", nome)
            .addValue("email", email)
            .addValue("senha", senha)
            .addValue("classe", classe), keyHolder, new String[] { "id" });
        return keyHolder.getKey().longValue();
    }

    public Long inserirCliente(String nome, String email, String telefone, Long idUsuario) {
        String sql = "INSERT INTO cliente (nome, email, telefone, id_usuario) "
                   + "VALUES (:nome, :email, :telefone, :idUsuario)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, new MapSqlParameterSource()
            .addValue("nome", nome)
            .addValue("email", email)
            .addValue("telefone", telefone)
            .addValue("idUsuario", idUsuario), keyHolder, new String[] { "id" });
        return keyHolder.getKey().longValue();
    }
}
