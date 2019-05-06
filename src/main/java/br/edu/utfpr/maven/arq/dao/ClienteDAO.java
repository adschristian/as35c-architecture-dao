package br.edu.utfpr.maven.arq.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import br.edu.utfpr.maven.arq.dto.ClienteDTO;
import br.edu.utfpr.maven.arq.dto.PaisDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.java.Log;

@Log
public class ClienteDAO {

    private final String URL = "jdbc:derby:memory:database;create=true";

    // Responsável por criar a tabela Cliente no banco.
    public ClienteDAO() {

        try (Connection conn = DriverManager.getConnection(URL)) {
            log.info("Criando tabela cliente ...");
            conn.createStatement().executeUpdate(
                    "CREATE TABLE cliente ("
                    + "id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT id_cliente_pk PRIMARY KEY,"
                    + "nome varchar(255),"
                    + "telefone varchar(30),"
                    + "idade int,"
                    + "limiteCredito double,"
                    + "id_pais int)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean inserir(ClienteDTO cliente) {
        String sql = "insert into cliente (nome, idade, telefone, limiteCredito, id_pais)"
                + "values (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = DriverManager.getConnection(URL).prepareStatement(sql)) {

            ps.setString(1, cliente.getNome());
            ps.setInt(2, cliente.getIdade());
            ps.setString(3, cliente.getTelefone());
            ps.setDouble(4, cliente.getLimiteCredito());
            ps.setInt(5, cliente.getPais().getId());

            int rows = ps.executeUpdate();
            if (rows > 0)
                return true;

            log.info("cliente inserido");
        } catch (SQLException e) {
            System.out.println("Error: " + 
                    e.getMessage());
        }
        return false;
    }

    public void alterar(ClienteDTO cliente) {
        String sql = "update cliente"
                + " set nome = ?, idade = ?, telefone = ?, limiteCredito = ?, id_pais = ?"
                + " where id = ?";
        try (PreparedStatement ps = DriverManager.getConnection(URL).prepareStatement(sql)) {

            ps.setString(1, cliente.getNome());
            ps.setInt(2, cliente.getIdade());
            ps.setString(3, cliente.getTelefone());
            ps.setDouble(4, cliente.getLimiteCredito());
            ps.setInt(5, cliente.getPais().getId());
            ps.setInt(6, cliente.getId());

            ps.executeUpdate();

            log.info("cliente alterado");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remover(int id) {
        String sql = "delete from cliente where id = ?";

        try (PreparedStatement ps = DriverManager.getConnection(URL).prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            log.info("cliente removido");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ClienteDTO> listarTodos() {
        String sql = "select * from cliente";

        List<ClienteDTO> clientes = new ArrayList<>();

        try (Statement st = DriverManager.getConnection(URL).createStatement()) {

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                ClienteDTO cliente = ClienteDTO.builder()
                        .id(rs.getInt("id"))
                        .nome(rs.getString("nome"))
                        .idade(rs.getInt("idade"))
                        .telefone(rs.getString("telefone"))
                        .limiteCredito(rs.getDouble("limiteCredito"))
                        .pais(PaisDTO.builder().id(rs.getInt("id_pais")).build())
                        .build();
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        log.info("sem registo de clientes");
        return clientes;
    }

    public ClienteDTO clientePorId(int id) {
        String sql = "select * from cliente join pais on cliente.id_pais = pais.id"
                + " where cliente.id = ?";

        ClienteDTO cliente;

        try (PreparedStatement ps = DriverManager.getConnection(URL).prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(sql);
            if (rs.next()) {
                cliente = ClienteDTO.builder()
                        .id(rs.getInt("cliente.id"))
                        .nome(rs.getString("cliente.nome"))
                        .idade(rs.getInt("cliente.idade"))
                        .telefone(rs.getString("cliente.telefone"))
                        .limiteCredito(rs.getDouble("cliente.limiteCredito"))
                        .pais(PaisDTO.builder()
                                .id(rs.getInt("pais.id"))
                                .nome(rs.getString("pais.nome"))
                                .sigla(rs.getString("pais.sigla"))
                                .codigoTelefone(rs.getInt("codigoTelefone"))
                                .build())
                        .build();
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("cliente não encontrado");
        return null;
    }
}
