package br.com.enuteo.api_reuso.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.enuteo.api_reuso.dto.pecas.AbstractPeca;
import br.com.enuteo.api_reuso.dto.pecas.Arroela;
import br.com.enuteo.api_reuso.dto.pecas.Parafuso;
import br.com.enuteo.api_reuso.dto.pecas.PecaFactory;
import br.com.enuteo.api_reuso.dto.pecas.Porca;

public class PecaMapper implements RowMapper<AbstractPeca>{

    @Override
    public AbstractPeca mapRow(ResultSet rs, int rowNum) throws SQLException {
        AbstractPeca peca = PecaFactory.criaPeca(rs.getString("nome"));
        if(peca instanceof Parafuso) return mapParafuso(rs);
        if(peca instanceof Arroela) return mapArroela(rs);
        if(peca instanceof Porca) return mapPorca(rs);
        return peca;
    }

    private static Parafuso mapParafuso(ResultSet rs) throws SQLException {
        Parafuso parafuso = new Parafuso();
        parafuso.setId(rs.getLong("id"));
        parafuso.setNome(rs.getString("nome"));
        parafuso.setQuantidade(rs.getInt("quantidade"));
        parafuso.setCategoria(rs.getString("categoria"));
        parafuso.setComprimento(rs.getInt("comprimento"));
        return parafuso;
    }

    private static Arroela mapArroela(ResultSet rs) throws SQLException {
        Arroela arroela = new Arroela();
        arroela.setId(rs.getLong("id"));
        arroela.setNome(rs.getString("nome"));
        arroela.setQuantidade(rs.getInt("quantidade"));
        arroela.setTipo(rs.getString("tipo"));
        arroela.setComprimento(rs.getInt("comprimento"));
        return arroela;
    }

    private static Porca mapPorca(ResultSet rs) throws SQLException {
        Porca porca = new Porca();
        porca.setId(rs.getLong("id"));
        porca.setNome(rs.getString("nome"));
        porca.setQuantidade(rs.getInt("quantidade"));
        porca.setTipo(rs.getString("tipo"));
        porca.setComprimento(rs.getInt("comprimento"));
        return porca;
    }

}
