package br.com.enuteo.api_reuso.repository;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.com.enuteo.api_reuso.dto.cliente.ClienteResponse;
import br.com.enuteo.api_reuso.dto.servico.ServicoResponse;

@Repository
public class ClienteRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ClienteRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<ClienteResponse> CLIENTE_MAPPER = (rs, n) ->
        new ClienteResponse(rs.getLong("id"), rs.getString("nome"),
            rs.getString("email"), rs.getString("telefone"));

    private static final RowMapper<ServicoResponse> SERVICO_MAPPER = (rs, n) ->
        new ServicoResponse(rs.getLong("id"), rs.getString("descricao"),
            rs.getBigDecimal("preco"), rs.getString("keyword"));

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

    /* ---------------- Consultas por usuário logado ---------------- */

    public ClienteResponse buscarPorUsuario(Long usuarioId) {
        try {
            return jdbcTemplate.queryForObject(
                "SELECT id, nome, email, telefone FROM cliente WHERE id_usuario = :uid",
                new MapSqlParameterSource("uid", usuarioId), CLIENTE_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<ServicoResponse> listarServicosPorUsuario(Long usuarioId) {
        String sql = "SELECT s.id, s.descricao, s.preco, s.keyword "
                   + "FROM cliente c "
                   + "JOIN servico_cliente sc ON sc.cliente_id = c.id "
                   + "JOIN servico s ON s.id = sc.servico_id "
                   + "WHERE c.id_usuario = :uid";
        return jdbcTemplate.query(sql, new MapSqlParameterSource("uid", usuarioId), SERVICO_MAPPER);
    }

    public String senhaDoUsuario(Long usuarioId) {
        return jdbcTemplate.queryForObject(
            "SELECT senha FROM usuario WHERE id = :uid",
            new MapSqlParameterSource("uid", usuarioId), String.class);
    }

    /* ---------------- Atualização de perfil ---------------- */

    public void atualizarUsuario(Long usuarioId, String nome, String email) {
        jdbcTemplate.update(
            "UPDATE usuario SET nome = :nome, email = :email WHERE id = :uid",
            new MapSqlParameterSource("nome", nome).addValue("email", email).addValue("uid", usuarioId));
    }

    public void atualizarSenha(Long usuarioId, String senhaHash) {
        jdbcTemplate.update(
            "UPDATE usuario SET senha = :senha WHERE id = :uid",
            new MapSqlParameterSource("senha", senhaHash).addValue("uid", usuarioId));
    }

    public void atualizarCliente(Long usuarioId, String nome, String email, String telefone) {
        jdbcTemplate.update(
            "UPDATE cliente SET nome = :nome, email = :email, telefone = :telefone WHERE id_usuario = :uid",
            new MapSqlParameterSource("nome", nome).addValue("email", email)
                .addValue("telefone", telefone).addValue("uid", usuarioId));
    }
}
