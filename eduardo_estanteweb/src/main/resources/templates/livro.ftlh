<#include "cabecalho.ftlh">

<h2>${livro.titulo}</h2>

<p>Autor: ${livro.autor}</p>
<p>Ano: ${livro.ano?c}</p>
<p>Preço: R$ ${livro.preco?c}</p>

<#if usuarioLogado??>
    <p><a href="/livros/${livro.id?c}/editar">Corrigir livro</a></p>
    <p><a href="/livros/${livro.id?c}/remover">Remover livro</a></p>
</#if>

<a href="/livros/html"> Voltar ao acervo </a>
