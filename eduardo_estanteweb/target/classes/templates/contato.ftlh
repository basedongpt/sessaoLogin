<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>

        <#include "cabecalho.ftlh">

        <h1>Entre em contato</h1>

        <#if erro??>
    <p class="erro">${erro}</p>
</#if>


            <form method="post" action="/contato">

                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" value="${nome!}">

                <br><br>

                <label for="mensagem">Mensagem:</label>
                <textarea id="mensagem" name="mensagem"></textarea>

                <br><br>

                <button type="submit">Enviar</button>

            </form>
</body>
</html>