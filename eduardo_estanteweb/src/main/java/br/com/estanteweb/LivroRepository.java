package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LivroRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Livro> findAll() {

        String sql = "SELECT * FROM livros";

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }

        return livros;
    }

    public List<Livro> listarAtePreco(double precoMaximo) {

        String sql = "SELECT * FROM livros WHERE preco <= ?";

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, precoMaximo);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }

        return livros;
    }

    public List<Livro> listarTodos() {

        String sql = "SELECT * FROM livros";

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }

        return livros;
    }

    public int contarLivros() {

        String sql = "SELECT COUNT(*) FROM livros";

        return jdbcTemplate.queryForObject(
                sql,
                Integer.class);
    }

    public void salvar(Livro livro) {

        String sql = "INSERT INTO livros (titulo, autor, ano, preco) " +
                "VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAno(),
                livro.getPreco());
    }

    public Livro buscarPorId(Long id) {
        String sql = "SELECT * FROM livros WHERE id = ?";

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql, id);

        if (linhas.isEmpty()) {
            return null;
        }

        return Livro.deMap(linhas.get(0));
    }

    public Livro listarOrdenadoPor(String coluna) {

        String sql = "SELECT * FROM livros ORDER BY " + coluna;

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {
            livros.add(Livro.deMap(linha));
        }

        return livros.get(0);
    }

    public int atualizar(Long id, Livro livro) {
        String sql = """
                UPDATE livros
                SET titulo = ?, autor = ?, ano = ?, preco = ?
                WHERE id = ?
                """;
        return jdbcTemplate.update(sql, livro.getTitulo(), livro.getAutor(), livro.getAno(), livro.getPreco(), id);
    }

    public int remover(Long id) {
        String sql = "DELETE FROM livros WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}