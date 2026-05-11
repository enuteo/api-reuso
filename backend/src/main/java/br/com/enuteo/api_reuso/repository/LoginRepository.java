package br.com.enuteo.api_reuso.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.enuteo.api_reuso.exception.InvalidUser;

@Repository
public class LoginRepository {
    
    @Autowired
    private DataSource dataSource;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public boolean userExists(String email, String password) throws InvalidUser {

        String sql = """
                SELECT COUNT(id) FROM usuario
                WHERE email = :email AND password = MD5(:password)
                """;
        
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        try {

            return jdbcTemplate.queryForObject(
                sql, 
                new MapSqlParameterSource("email", email).addValue("password", password), 
                Integer.class
            ) > 0;
            
        } catch (EmptyResultDataAccessException e) {
            throw new InvalidUser("Usuário ou senha inválidos");
        }

    }
}
