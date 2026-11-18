<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sugestões</title>
</head>
<body>

    <h1>Sugestões de livros</h1>

    <ul>
        <#list sugestoes as sugestao>
            <li>
                ${sugestao.nomeCliente},
                ${sugestao.livroSugerido}
            </li>
        </#list>
    </ul>

</body>
</html>