package dao;

import model.Paciente;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    // Salvar novo paciente
    public void salvar(Paciente paciente) {
        String sql = "INSERT INTO pacientes (nome, bi, data_nascimento, telefone, email, endereco, historico_medico, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getBi());
            stmt.setString(3, paciente.getDataNascimento());
            stmt.setString(4, paciente.getTelefone());
            stmt.setString(5, paciente.getEmail());
            stmt.setString(6, paciente.getEndereco());
            stmt.setString(7, paciente.getHistoricoMedico());
            stmt.setBoolean(8, paciente.isAtivo());
            stmt.executeUpdate();
            System.out.println("Paciente salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar paciente: " + e.getMessage());
        }
    }

    // Buscar todos os pacientes
    public List<Paciente> buscarTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes WHERE ativo = true";
        try {
            Connection conn = Conexao.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setBi(rs.getString("bi"));
                p.setDataNascimento(rs.getString("data_nascimento"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setEndereco(rs.getString("endereco"));
                p.setHistoricoMedico(rs.getString("historico_medico"));
                p.setAtivo(rs.getBoolean("ativo"));
                pacientes.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pacientes: " + e.getMessage());
        }
        return pacientes;
    }

    // Atualizar paciente
    public void atualizar(Paciente paciente) {
        String sql = "UPDATE pacientes SET nome=?, bi=?, data_nascimento=?, telefone=?, email=?, endereco=?, historico_medico=? WHERE id=?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getBi());
            stmt.setString(3, paciente.getDataNascimento());
            stmt.setString(4, paciente.getTelefone());
            stmt.setString(5, paciente.getEmail());
            stmt.setString(6, paciente.getEndereco());
            stmt.setString(7, paciente.getHistoricoMedico());
            stmt.setInt(8, paciente.getId());
            stmt.executeUpdate();
            System.out.println("Paciente atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar paciente: " + e.getMessage());
        }
    }

    // Inativar paciente (nunca apagamos, só inativamos)
    public void inativar(int id) {
        String sql = "UPDATE pacientes SET ativo = false WHERE id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Paciente inativado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inativar paciente: " + e.getMessage());
        }
    }

    // Buscar por nome ou Bi
    public List<Paciente> buscarPorNomeOuBi(String termo) {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes WHERE ativo = true AND (nome LIKE ? OR bi LIKE ?)";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + termo + "%");
            stmt.setString(2, "%" + termo + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paciente p = new Paciente();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setBi(rs.getString("bi"));
                p.setDataNascimento(rs.getString("data_nascimento"));
                p.setTelefone(rs.getString("telefone"));
                p.setEmail(rs.getString("email"));
                p.setEndereco(rs.getString("endereco"));
                p.setHistoricoMedico(rs.getString("historico_medico"));
                p.setAtivo(rs.getBoolean("ativo"));
                pacientes.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar paciente: " + e.getMessage());
        }
        return pacientes;
    }
    public int buscarIdPorUsuario(int usuarioId) {
        String sql = "SELECT id FROM pacientes WHERE usuario_id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar paciente por usuário: " + e.getMessage());
        }
        return -1;
    }
}