package br.com.estanteweb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.ui.Model;

@Controller
public class LivroController {

    @Autowired
    private LivroRepository repositorio;

    @GetMapping(value = "/livros/html")

    public String listarLivrosHtml(Model model, @RequestParam(required = false) String ordem) {

        model.addAttribute("livros", repositorio.listarTodos());

        return "livros";
    }

    @GetMapping("/livros")
    @ResponseBody
    public String listarLivros() {

        String resposta = "Acervo EstanteWeb: ";

        for (Livro livro : repositorio.listarTodos()) {

            resposta += livro.getTitulo()
                    + ", de "
                    + livro.getAutor()
                    + " (R$ "
                    + livro.getPreco()
                    + ") | ";
        }

        return resposta;
    }

    @GetMapping("/livros/total")
    @ResponseBody
    public String totalLivros() {

        return "O acervo tem "
                + repositorio.contarLivros()
                + " livros.";
    }

    @GetMapping("/livros/busca")
    public ResponseEntity<String> buscarPorPreco(
            @RequestParam(defaultValue = "999") double precoMaximo) {

        List<Livro> livrosEncontrados = repositorio.listarAtePreco(precoMaximo);

        // Nenhum livro encontrado
        if (livrosEncontrados.isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Nenhum livro encontrado por até R$ "
                            + precoMaximo + ".");
        }

        // Livros encontrados
        String resposta = "Livros até R$ "
                + precoMaximo + ": ";

        for (Livro livro : livrosEncontrados) {

            resposta += livro.getTitulo()
                    + ", de "
                    + livro.getAutor()
                    + " (R$ "
                    + livro.getPreco()
                    + ") | ";
        }

        return ResponseEntity
                .ok()
                .header(
                        "X-Total-Encontrados",
                        String.valueOf(livrosEncontrados.size()))
                .body(resposta);
    }

    @GetMapping("/livros/quem-acessa")
    @ResponseBody
    public String quemAcessa(
            @RequestHeader("User-Agent") String userAgent) {

        return "O acesso veio de: " + userAgent;
    }

    @GetMapping("/livros/novo")
    public String novoLivro() {
        return "livro-novo";
    }

    @PostMapping("/livros")
    public String cadastrar(
            Model model,
            @RequestParam(defaultValue = "") String titulo,
            @RequestParam(defaultValue = "") String autor,
            @RequestParam(defaultValue = "") String ano,
            @RequestParam(defaultValue = "") String preco) {

        Livro livro;
        try {
            livro = validarDados(titulo, autor, ano, preco);
        } catch (IllegalArgumentException e) {
            return exibirFormularioComErro(model, titulo, autor, ano, preco, e.getMessage());
        }

        repositorio.salvar(livro);

        return "redirect:/livros";
    }

    private Livro validarDados(String titulo, String autor, String ano, String preco) {
        int anoNumerico;
        double precoNumerico;

        try {
            anoNumerico = Integer.parseInt(ano.trim());
            precoNumerico = Double.parseDouble(preco.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe um ano inteiro e um preço numérico válido.");
        }

        if (titulo.isBlank() || autor.isBlank() || anoNumerico <= 0
                || precoNumerico <= 0 || !Double.isFinite(precoNumerico)) {
            throw new IllegalArgumentException(
                    "Preencha todos os campos. Ano e preço devem ser maiores que zero.");
        }

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno(anoNumerico);
        livro.setPreco(precoNumerico);
        return livro;
    }

    private String exibirFormularioComErro(
            Model model, String titulo, String autor, String ano, String preco, String erro) {
        model.addAttribute("erro", erro);
        model.addAttribute("titulo", titulo);
        model.addAttribute("autor", autor);
        model.addAttribute("ano", ano);
        model.addAttribute("preco", preco);
        return "livro-novo";
    }

    private static class LivroNaoEncontradoException extends RuntimeException {
    }

    private Livro livroOu404(Long id) {
        Livro livro = repositorio.buscarPorId(id);
        if (livro == null) {
            throw new LivroNaoEncontradoException();
        }
        return livro;
    }

    @ExceptionHandler(LivroNaoEncontradoException.class)
    public ResponseEntity<String> livroNaoEncontrado() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livro não encontrado.");
    }

    private String exibirFormularioEdicao(Model model, Long id, String titulo, String autor,
            String ano, String preco, String erro) {
        model.addAttribute("id", id);
        model.addAttribute("titulo", titulo);
        model.addAttribute("autor", autor);
        model.addAttribute("ano", ano);
        model.addAttribute("preco", preco);
        if (erro != null) {
            model.addAttribute("erro", erro);
        }
        return "livro-editar";
    }

    @GetMapping("/livros/{id}/editar")
    public String editarLivro(Model model, @PathVariable Long id,
            @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return "redirect:/login";
        }
        Livro livro = livroOu404(id);
        return exibirFormularioEdicao(model, id, livro.getTitulo(), livro.getAutor(),
                Integer.toString(livro.getAno()), Double.toString(livro.getPreco()), null);
    }

    @PostMapping("/livros/{id}/editar")
    public String atualizarLivro(Model model, @PathVariable Long id,
            @RequestParam(defaultValue = "") String titulo,
            @RequestParam(defaultValue = "") String autor,
            @RequestParam(defaultValue = "") String ano,
            @RequestParam(defaultValue = "") String preco,
            @SessionAttribute(name = "usuarioLogado", required = false) Usuario usuarioLogado) {
        if (usuarioLogado == null) {
            return "redirect:/login";
        }
        livroOu404(id);

        Livro livro;
        try {
            livro = validarDados(titulo, autor, ano, preco);
        } catch (IllegalArgumentException e) {
            return exibirFormularioEdicao(model, id, titulo, autor, ano, preco, e.getMessage());
        }

        if (repositorio.atualizar(id, livro) == 0) {
            livroOu404(id);
        }
        return "redirect:/livros/" + id;
    }

    @GetMapping("/livros/{id}")
    public String buscarPorId(
            Model model,
            @PathVariable Long id) {

        model.addAttribute("livro", livroOu404(id));

        return "livro";
    }

    @GetMapping("/livros/{id}/remover")
    public String confirmarRemocao(Model model, @PathVariable Long id) {

        model.addAttribute("livro", livroOu404(id));

        return "livro-remover";
    }

    @PostMapping("/livros/{id}/remover")
    public String remover(@PathVariable Long id) {
        int linhas = repositorio.remover(id);

        if (linhas == 0) {
            throw new LivroNaoEncontradoException();
        }

        return "redirect:/livros/html";
    }
}
