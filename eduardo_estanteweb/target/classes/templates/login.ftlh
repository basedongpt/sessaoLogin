<#include "cabecalho.ftlh">

<h2>Entrar</h2>

<#if erro??>
    <p role="alert">${erro}</p>
</#if>

<form method="post" action="/login">
    <p>
        <label for="email">E-mail</label>
        <input id="email" type="email" name="email" value="${email!}" required>
    </p>
    <p>
        <label for="senha">Senha</label>
        <input id="senha" type="password" name="senha" required>
    </p>
    <button type="submit">Entrar</button>
</form>
