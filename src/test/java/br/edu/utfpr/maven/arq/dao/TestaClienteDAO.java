package br.edu.utfpr.maven.arq.dao;

import br.edu.utfpr.maven.arq.dto.ClienteDTO;
import br.edu.utfpr.maven.arq.dto.PaisDTO;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author christian
 */
public class TestaClienteDAO {

    private static ClienteDAO clienteDao;

    @BeforeClass
    public static void setup() {
        clienteDao = new ClienteDAO();
    }

    @Test
    public void testaInserir() {
        ClienteDTO cliente = ClienteDTO.builder()
                .idade(14)
                .limiteCredito(100)
                .nome("John Connor")
                .pais(PaisDTO.builder().build())
                .telefone("0129483223")
                .build();

        Assert.assertTrue(clienteDao.inserir(cliente));
    }

    @Test
    //@Ignore("fail test")
    public void testaListar() {
        ClienteDTO cliente = ClienteDTO.builder()
                .idade(34)
                .limiteCredito(100)
                .nome("Sarah Connor")
                .pais(PaisDTO.builder().build())
                .telefone("0129483223")
                .build();
        clienteDao.inserir(cliente);
        Assert.assertTrue(clienteDao.listarTodos().size() > 0);
    }
}
