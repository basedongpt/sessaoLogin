<#include "cabecalho.ftlh">

<h2>Remover Livro</h2>

<p> Você está prestes a remover o livro <strong>${livro.titulo}</strong>, de ${livro.autor}.</p>

<form method="post" action="/livros/${livro.id?c}/remover">
    <button type="submit">Confirmar remoção</button>
</form>

<a href="/livros/${livro.id?c}">Cancelar</a>
