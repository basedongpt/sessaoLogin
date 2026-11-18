
package br.com.estanteweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SugestaoController {

    private final SugestaoRepository repositorio;

    public SugestaoController(SugestaoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping("/sugestoes/nova")
    public String novaSugestao(Model model) {
        model.addAttribute("nomeCliente", "");
        model.addAttribute("livroSugerido", "");
        return "sugestao-nova";
    }

    @PostMapping("/sugestoes")
    public String receberSugestao(
            @RequestParam String nomeCliente,
            @RequestParam String livroSugerido,
            Model model) {

        if (nomeCliente.isBlank() || livroSugerido.isBlank()) {

            model.addAttribute(
                    "erro",
                    "Nome do cliente e livro sugerido não podem ficar em branco."
            );

            model.addAttribute("nomeCliente", nomeCliente);
            model.addAttribute("livroSugerido", livroSugerido);

            return "sugestao-nova";
        }

        Sugestao sugestao = new Sugestao(
                nomeCliente,
                livroSugerido
        );

        repositorio.salvar(sugestao);

        return "redirect:/sugestoes";
    }

    @GetMapping("/sugestoes")
    public String listarSugestoes(Model model) {

        model.addAttribute(
                "sugestoes",
                repositorio.listarTodos()
        );

        return "sugestoes";
    }
}