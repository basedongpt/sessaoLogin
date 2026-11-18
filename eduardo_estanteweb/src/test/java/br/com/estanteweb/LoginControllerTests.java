package br.com.estanteweb;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

class LoginControllerTests {
    private UsuarioRepository usuarios;
    private LivroRepository livros;
    private MockMvc mvc;

    @BeforeEach
    void preparar() {
        usuarios = mock(UsuarioRepository.class);
        livros = mock(LivroRepository.class);
        LivroController livroController = new LivroController();
        ReflectionTestUtils.setField(livroController, "repositorio", livros);
        InternalResourceViewResolver views = new InternalResourceViewResolver();
        views.setPrefix("/WEB-INF/test/");
        views.setSuffix(".ftlh");
        mvc = MockMvcBuilders.standaloneSetup(new LoginController(usuarios), livroController)
                .setViewResolvers(views).build();
    }

    @Test
    void loginInvalidoUsaMesmaMensagemEConservaEmail() throws Exception {
        Usuario marta = new Usuario();
        marta.setEmail("marta@estanteweb.com");
        marta.setSenha("estante123");
        when(usuarios.buscarPorEmail(marta.getEmail())).thenReturn(marta);

        mvc.perform(post("/login").param("email", marta.getEmail()).param("senha", "errada"))
                .andExpect(status().isOk()).andExpect(view().name("login"))
                .andExpect(model().attribute("erro", "E-mail ou senha inválidos."))
                .andExpect(model().attribute("email", marta.getEmail()));
        mvc.perform(post("/login").param("email", "desconhecido@exemplo.com").param("senha", "errada"))
                .andExpect(status().isOk()).andExpect(view().name("login"))
                .andExpect(model().attribute("erro", "E-mail ou senha inválidos."))
                .andExpect(model().attribute("email", "desconhecido@exemplo.com"));
    }

    @Test
    void loginCorretoCriaSessaoELogoutInvalida() throws Exception {
        Usuario marta = new Usuario();
        marta.setEmail("marta@estanteweb.com");
        marta.setSenha("estante123");
        when(usuarios.buscarPorEmail(marta.getEmail())).thenReturn(marta);

        var resultado = mvc.perform(post("/login").param("email", marta.getEmail())
                .param("senha", "estante123"))
                .andExpect(redirectedUrl("/livros/html")).andReturn();
        MockHttpSession sessao = (MockHttpSession) resultado.getRequest().getSession(false);
        org.junit.jupiter.api.Assertions.assertSame(marta, sessao.getAttribute("usuarioLogado"));
        mvc.perform(post("/logout").session(sessao)).andExpect(redirectedUrl("/login"));
        org.junit.jupiter.api.Assertions.assertTrue(sessao.isInvalid());
    }

    @Test
    void visitanteNaoPodeAbrirNemGravarEdicao() throws Exception {
        mvc.perform(get("/livros/1/editar")).andExpect(redirectedUrl("/login"));
        mvc.perform(post("/livros/1/editar").param("titulo", "Livro")
                .param("autor", "Autor").param("ano", "2020").param("preco", "10"))
                .andExpect(redirectedUrl("/login"));
        verifyNoInteractions(livros);
    }

    @Test
    void usuarioLogadoPodeAbrirEdicao() throws Exception {
        Livro livro = new Livro();
        livro.setTitulo("Livro");
        livro.setAutor("Autor");
        livro.setAno(2020);
        livro.setPreco(10);
        when(livros.buscarPorId(1L)).thenReturn(livro);
        MockHttpSession sessao = new MockHttpSession();
        sessao.setAttribute("usuarioLogado", new Usuario());

        mvc.perform(get("/livros/1/editar").session(sessao))
                .andExpect(status().isOk()).andExpect(view().name("livro-editar"))
                .andExpect(model().attribute("titulo", "Livro"));
    }
}
