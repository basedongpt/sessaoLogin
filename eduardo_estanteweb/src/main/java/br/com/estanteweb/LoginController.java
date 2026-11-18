package br.com.estanteweb;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final UsuarioRepository repositorio;

    public LoginController(UsuarioRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String entrar(@RequestParam(defaultValue = "") String email,
            @RequestParam(defaultValue = "") String senha,
            Model model, HttpServletRequest request) {
        Usuario usuario = repositorio.buscarPorEmail(email);
        if (usuario == null || !usuario.getSenha().equals(senha)) {
            model.addAttribute("erro", "E-mail ou senha inválidos.");
            model.addAttribute("email", email);
            return "login";
        }

        HttpSession sessao = request.getSession(true);
        request.changeSessionId();
        sessao.setAttribute("usuarioLogado", usuario);
        return "redirect:/livros/html";
    }

    @PostMapping("/logout")
    public String sair(HttpServletRequest request) {
        HttpSession sessao = request.getSession(false);
        if (sessao != null) {
            sessao.invalidate();
        }
        return "redirect:/login";
    }
}
