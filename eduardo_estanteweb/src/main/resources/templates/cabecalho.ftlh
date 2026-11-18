<!-- cabecalho.ftlh -->
<header>
  <h1>Livros</h1>
  <nav><a href="/">Início</a> | <a href="/livros/html">Catalogo</a></nav>
  <#if usuarioLogado??>
    <p>Olá, ${usuarioLogado.nome}</p>
    <form method="post" action="/logout">
      <button type="submit">Sair</button>
    </form>
  <#else>
    <a href="/login">Entrar</a>
  </#if>
</header>
