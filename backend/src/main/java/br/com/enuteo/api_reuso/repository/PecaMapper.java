package br.com.enuteo.api_reuso.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import br.com.enuteo.api_reuso.dto.pecas.AbstractPeca;
import br.com.enuteo.api_reuso.dto.pecas.Parafuso;
import br.com.enuteo.api_reuso.dto.pecas.PecaFactory;

public class PecaMapper implements RowMapper<AbstractPeca>{

    @Override
    public AbstractPeca mapRow(ResultSet rs, int rowNum) throws SQLException {
        String nomeNoBanco = rs.getString("nome");
        AbstractPeca peca;
        
        try {
            // Attempts to route through the strict Factory
            peca = PecaFactory.criaPeca(nomeNoBanco);
        } catch (Exception e) {
            // Fallback safety net: prevents a server crash if an unknown part is in the DB
            peca = new Parafuso(); 
        }

        // Only maps columns that physically exist in the SQL schema
        peca.setId(rs.getLong("id"));
        peca.setNome(nomeNoBanco);
        peca.setQuantidade(rs.getInt("quantidade"));
        
        // Ignored 'categoria' and 'comprimento' to avoid SQLException
        return peca;
    }
}