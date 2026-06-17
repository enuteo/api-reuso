package br.com.enuteo.api_reuso.config;

import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Migração idempotente de senhas para BCrypt.
 * Roda na inicialização e só age nas linhas cujo `senha` ainda não é um hash ($2...).
 * Torna o seed (senhas em texto puro de db_reuso.sql) auto-corrigível no primeiro boot.
 */
@Component
public class PasswordMigrationRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(PasswordMigrationRunner.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    public PasswordMigrationRunner(DataSource dataSource, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        List<Map<String, Object>> pendentes = jdbcTemplate.queryForList(
            "SELECT id, senha FROM usuario WHERE senha NOT LIKE '$2%'", new MapSqlParameterSource());

        for (Map<String, Object> row : pendentes) {
            Long id = ((Number) row.get("id")).longValue();
            String senhaPlana = (String) row.get("senha");
            String hash = passwordEncoder.encode(senhaPlana);
            jdbcTemplate.update(
                "UPDATE usuario SET senha = :senha WHERE id = :id",
                new MapSqlParameterSource("senha", hash).addValue("id", id));
        }

        if (!pendentes.isEmpty()) {
            log.info("Migração de senhas: {} senha(s) convertida(s) para BCrypt.", pendentes.size());
        }
    }
}
