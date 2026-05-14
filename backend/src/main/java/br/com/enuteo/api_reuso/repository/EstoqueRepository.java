package br.com.enuteo.api_reuso.repository;

import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.enuteo.api_reuso.dto.pecas.AbstractPeca;

// Exemplo de Singleton - anotacao repository ja faz o singleton
@Repository
public class EstoqueRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EstoqueRepository(DataSource dataSource) {
        // Inicializa o template de conexão apenas uma vez (Melhor prática)
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    // Novo método que vai no banco e traz todas as peças da tabela
    public List<AbstractPeca> listarTodas() {
        String sql = "SELECT * FROM pecas"; // Tabela corrigida para o plural
        return jdbcTemplate.query(sql, new PecaMapper());
    }

    public AbstractPeca buscarPeca(Long id, String nome) {
        String sql = "SELECT * FROM pecas WHERE nome = :nome AND id = :id"; // Tabela corrigida para o plural

        return jdbcTemplate.queryForObject(
            sql,
            new MapSqlParameterSource("nome", nome).addValue("id", id),
            new PecaMapper()
        );
    }
}