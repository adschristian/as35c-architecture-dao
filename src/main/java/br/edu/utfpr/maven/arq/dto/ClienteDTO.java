package br.edu.utfpr.maven.arq.dto;

import br.edu.utfpr.maven.arq.excecao.NomeClienteMenor5CaracteresException;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClienteDTO {

    private int id;
    private String nome;
    private int idade;
    private String telefone;
    private double limiteCredito;
    private PaisDTO pais;

    public void setNome(String nome) throws NomeClienteMenor5CaracteresException {
        if (nome.length() < 5) {
            throw new NomeClienteMenor5CaracteresException(nome);
        }
        this.nome = nome;
    }
}
