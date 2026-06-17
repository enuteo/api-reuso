package br.com.enuteo.api_reuso.repository;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import br.com.enuteo.api_reuso.dto.estoque.EstoqueItem;
import br.com.enuteo.api_reuso.dto.pecas.Peca;

@Repository
public class EstoqueRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EstoqueRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<Peca> PECA_MAPPER = (rs, rowNum) -> {
        Peca p = new Peca();
        p.setId(rs.getLong("id"));
        p.setNome(rs.getString("nome"));
        p.setPreco(rs.getBigDecimal("preco"));
        return p;
    };

    private static final RowMapper<EstoqueItem> ESTOQUE_MAPPER = (rs, rowNum) -> {
        EstoqueItem e = new EstoqueItem();
        e.setId(rs.getLong("id"));
        e.setPecaId(rs.getLong("peca_id"));
        e.setPecaNome(rs.getString("peca_nome"));
        e.setQuantidade(rs.getInt("quantidade"));
        return e;
    };

    /* ---------------- Catálogo de peças (tabela `peca`) ---------------- */

    public List<Peca> listarPecas() {
        return jdbcTemplate.query("SELECT id, nome, preco FROM peca", PECA_MAPPER);
    }

    public Peca buscarPeca(Long id) {
        return jdbcTemplate.queryForObject(
            "SELECT id, nome, preco FROM peca WHERE id = :id",
            new MapSqlParameterSource("id", id),
            PECA_MAPPER);
    }

    public Peca inserir(Peca peca) {
        String sql = "INSERT INTO peca (nome, preco) VALUES (:nome, :preco)";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("nome", peca.getNome())
            .addValue("preco", peca.getPreco());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder, new String[] { "id" });

        if (keyHolder.getKey() != null) {
            peca.setId(keyHolder.getKey().longValue());
        }
        return peca;
    }

    public int atualizar(Long id, Peca peca) {
        return jdbcTemplate.update(
            "UPDATE peca SET nome = :nome, preco = :preco WHERE id = :id",
            new MapSqlParameterSource()
                .addValue("nome", peca.getNome())
                .addValue("preco", peca.getPreco())
                .addValue("id", id));
    }

    public int deletar(Long id) {
        return jdbcTemplate.update(
            "DELETE FROM peca WHERE id = :id",
            new MapSqlParameterSource("id", id));
    }

    // Remove os registros de estoque que referenciam a peça (FK fk_estoque_peca).
    public int deletarEstoquePorPeca(Long pecaId) {
        return jdbcTemplate.update(
            "DELETE FROM estoque WHERE id_peca = :pid",
            new MapSqlParameterSource("pid", pecaId));
    }

    /* ---------------- Inventário (tabela `estoque` JOIN `peca`) -------- */

    public List<EstoqueItem> listarEstoque() {
        String sql = "SELECT e.id AS id, e.id_peca AS peca_id, p.nome AS peca_nome, e.quantidade AS quantidade "
                   + "FROM estoque e JOIN peca p ON p.id = e.id_peca";
        return jdbcTemplate.query(sql, ESTOQUE_MAPPER);
    }
}
