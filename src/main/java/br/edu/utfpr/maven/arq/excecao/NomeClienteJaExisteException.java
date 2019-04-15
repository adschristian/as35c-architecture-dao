package br.edu.utfpr.maven.arq.excecao;

public class NomeClienteJaExisteException extends Exception {
    public NomeClienteJaExisteException (String descricao) {
        super(descricao);
    }
}