package br.edu.utfpr.maven.arq.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaisDTO {
    private int id;
    private String nome;
    private String sigla;
    private int codigoTelefone;

}