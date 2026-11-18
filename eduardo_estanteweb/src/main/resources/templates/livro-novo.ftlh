<#include "cabecalho.ftlh">

<#if erro??>
    <p>${erro}</p>
</#if>

<form method="post" action="/livros">
    <input type="text" name="titulo" placeholder="Título" value="${titulo!}" required>
    <input type="text" name="autor" placeholder="Autor" value="${autor!}" required>
    <input type="number" name="ano" placeholder="Ano" value="${ano!}" min="1" step="1" required>
    <input type="number" name="preco" placeholder="Preço" value="${preco!}" min="0.01" step="0.01" required>

    <button type="submit">Cadastrar</button>
</form>
