<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catálogo de livros</title>
    <style>
        .tabela-catalogo { overflow-x: auto; }
        table { border-collapse: collapse; }
        th, td { padding: 0.4rem 1.5rem 0.4rem 0; text-align: left; }
        .acoes { white-space: nowrap; }
    </style>
</head>
<body>
    <#include "cabecalho.ftlh">

<#if livros?size == 0>
<p>Nenhum livro encontrado</p>
<#else>

    <div class="tabela-catalogo">
        <table>
            <thead>
                <tr>
                    <th scope="col">Título</th>
                    <th scope="col">Autor</th>
                    <th scope="col">Preço</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                <#list livros as livro>
                <tr>
                    <td>${(livro.titulo)!"(sem título)"}</td>
                    <td>${(livro.autor)!"(autor não informado)"}</td>
                    <td>R$ ${livro.preco?c}</td>
                    <td class="acoes">
                        <a href="/livros/${livro.id?c}">Detalhes</a>
                        <#if usuarioLogado??>
                            | <a href="/livros/${livro.id?c}/editar">Editar</a>
                            | <a href="/livros/${livro.id?c}/remover">Excluir</a>
                        </#if>
                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
</#if>

</body>
</html>
