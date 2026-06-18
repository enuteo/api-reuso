package br.com.enuteo.api_reuso.repository;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.enuteo.api_reuso.dto.usuario.UsuarioResumo;

@Repository
public class UsuarioRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UsuarioRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<UsuarioResumo> RESUMO_MAPPER = (rs, n) ->
        new UsuarioResumo(rs.getLong("id"), rs.getString("nome"),
            rs.getString("email"), rs.getInt("classe"));

    public List<UsuarioResumo> listar() {
        return jdbcTemplate.query(
            "SELECT id, nome, email, classe FROM usuario LIMIT 25",
            RESUMO_MAPPER);
    }

    // Cria o perfil de funcionário vinculado ao usuário (a tabela `funcionario` exige `cargo`).
    public void inserirFuncionario(String nome, String cargo, Long idUsuario) {
        jdbcTemplate.update(
            "INSERT INTO funcionario (nome, cargo, id_usuario) VALUES (:nome, :cargo, :idUsuario)",
            new MapSqlParameterSource("nome", nome)
                .addValue("cargo", cargo)
                .addValue("idUsuario", idUsuario));
    }

    /**
     * Exclusão FÍSICA do usuário, com cascata manual para respeitar as FKs:
     * servico_cliente / acesso_funcionario -> cliente / funcionario -> usuario.
     */
    public void deletarFisico(Long usuarioId) {
        MapSqlParameterSource p = new MapSqlParameterSource("uid", usuarioId);
        jdbcTemplate.update(
            "DELETE FROM servico_cliente WHERE cliente_id IN (SELECT id FROM cliente WHERE id_usuario = :uid)", p);
        jdbcTemplate.update(
            "DELETE FROM acesso_funcionario WHERE funcionario_id IN (SELECT id FROM funcionario WHERE id_usuario = :uid)", p);
        jdbcTemplate.update("DELETE FROM cliente WHERE id_usuario = :uid", p);
        jdbcTemplate.update("DELETE FROM funcionario WHERE id_usuario = :uid", p);
        jdbcTemplate.update("DELETE FROM usuario WHERE id = :uid", p);
    }
}
