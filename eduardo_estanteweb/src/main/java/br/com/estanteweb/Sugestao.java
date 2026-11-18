package br.com.estanteweb;

public class Sugestao {
    private String nomeCliente;
    private String livroSugerido;

    public String getLivroSugerido() {
        return livroSugerido;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    Sugestao(String nomeCliente, String livroSugerido){
        this.livroSugerido = livroSugerido;
        this.nomeCliente = nomeCliente;
    }
}
