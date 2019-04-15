package br.edu.utfpr.maven.arq.negocio;

import br.edu.utfpr.maven.arq.dao.ClienteDAO;
import java.util.List;

import br.edu.utfpr.maven.arq.dto.ClienteDTO;
import br.edu.utfpr.maven.arq.excecao.NomeClienteJaExisteException;

public class ClienteNegocio {
    
    private ClienteDAO dao;
    
    public ClienteNegocio() {
        this.dao = new ClienteDAO();
    }
    
    public void incluir(ClienteDTO cliente) throws NomeClienteJaExisteException {

        if (this.listar().stream().anyMatch(c -> c.getNome().equalsIgnoreCase(cliente.getNome()))) {
            throw new NomeClienteJaExisteException(cliente.getNome());
        }

        this.dao.inserir(cliente);
    }

    public List<ClienteDTO> listar() {
        return this.dao.listarTodos();
    }
}
