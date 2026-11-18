package br.com.estanteweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaginaController {

    @GetMapping("/")
    @ResponseBody
    public String inicio() {
        return "EstanteWeb: livros usados com história";
    }

    @GetMapping("/sobre")
    @ResponseBody
    public String sobre() {
        return "A EstanteWeb é um sebo especializado na venda de livros usados.";
    }

    @GetMapping("/contato")
    public String contato(Model model) {
        model.addAttribute("nome", "");
        model.addAttribute("mensagem", "");
        return "contato";
    }

    @PostMapping("/contato")
    public String receberContato(
            @RequestParam String nome,
            @RequestParam String mensagem,
            Model model) {

        if (mensagem.isBlank()) {
            model.addAttribute("erro", "A mensagem não pode estar em branco.");
            model.addAttribute("nome", nome);
            model.addAttribute("mensagem", mensagem);
            return "contato";
        }

        return "redirect:/contato/sucesso";
    }

    @GetMapping("/contato/sucesso")
    public String contatoSucesso(Model model) {
        model.addAttribute("nome", "contato");
        model.addAttribute("mensagem", "contato");
        return "contato-sucesso";
    }
}