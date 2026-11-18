<#include "cabecalho.ftlh">

<h2>Corrigir livro</h2>

<#if erro??>
    <p role="alert">${erro}</p>
</#if>

<form method="post" action="/livros/${id?c}/editar">
    <p>
        <label for="titulo">Título</label>
        <input id="titulo" type="text" name="titulo"
               value="${titulo!}" required>
    </p>

    <p>
        <label for="autor">Autor</label>
        <input id="autor" type="text" name="autor"
               value="${autor!}" required>
    </p>

    <p>
        <label for="ano">Ano</label>
        <input id="ano" type="text" inputmode="numeric" name="ano"
               value="${ano!}" required>
    </p>

    <p>
        <label for="preco">Preço</label>
        <input id="preco" type="text" inputmode="decimal" name="preco"
               value="${preco!}" required>
    </p>

    <button type="submit">Salvar alterações</button>
    <a href="/livros/${id?c}">Cancelar</a>
</form>