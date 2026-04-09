package br.com.enuteo.api_reuso.repository;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.enuteo.api_reuso.dto.pecas.AbstractPeca;

// Exemplo de Singleton - anotacao repository ja faz o singleton
@Repository
public class EstoqueRepository {

    @Autowired
    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public AbstractPeca buscarPeca(Long id, String nome) {
        
        String sql = "SELECT * FROM peca WHERE nome = :nome AND id = :id";

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        return jdbcTemplate.queryForObject(
            sql,
            new MapSqlParameterSource("nome", nome).addValue("id", id),
            new PecaMapper()
        );
        
    }
    
}
