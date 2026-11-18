package br.com.estanteweb;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {
    private final JdbcTemplate jdbcTemplate;

    public UsuarioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Usuario buscarPorEmail(String email) {
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(
                "SELECT id, nome, email, senha FROM usuario WHERE email = ?", email);
        return linhas.isEmpty() ? null : Usuario.deMap(linhas.get(0));
    }
}
