package dao;

import model.Medico;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    // Salvar novo médico
    public void salvar(Medico medico) {
        String sql = "INSERT INTO medicos (nome, crm, especialidade, telefone, email, ativo) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.getTelefone());
            stmt.setString(5, medico.getEmail());
            stmt.setBoolean(6, medico.isAtivo());
            stmt.executeUpdate();
            System.out.println("Médico salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar médico: " + e.getMessage());
        }
    }

    // Buscar todos os médicos
    public List<Medico> buscarTodos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medicos WHERE ativo = true";
        try {
            Connection conn = Conexao.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setCrm(rs.getString("crm"));
                m.setEspecialidade(rs.getString("especialidade"));
                m.setTelefone(rs.getString("telefone"));
                m.setEmail(rs.getString("email"));
                m.setAtivo(rs.getBoolean("ativo"));
                medicos.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar médicos: " + e.getMessage());
        }
        return medicos;
    }

    // Atualizar médico
    public void atualizar(Medico medico) {
        String sql = "UPDATE medicos SET nome=?, crm=?, especialidade=?, telefone=?, email=? WHERE id=?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setString(3, medico.getEspecialidade());
            stmt.setString(4, medico.getTelefone());
            stmt.setString(5, medico.getEmail());
            stmt.setInt(6, medico.getId());
            stmt.executeUpdate();
            System.out.println("Médico atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar médico: " + e.getMessage());
        }
    }

    // Inativar médico
    public void inativar(int id) {
        String sql = "UPDATE medicos SET ativo = false WHERE id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Médico inativado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inativar médico: " + e.getMessage());
        }
    }

    // Buscar por nome ou CRM
    public List<Medico> buscarPorNomeOuCrm(String termo) {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medicos WHERE ativo = true AND (nome LIKE ? OR crm LIKE ?)";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + termo + "%");
            stmt.setString(2, "%" + termo + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Medico m = new Medico();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setCrm(rs.getString("crm"));
                m.setEspecialidade(rs.getString("especialidade"));
                m.setTelefone(rs.getString("telefone"));
                m.setEmail(rs.getString("email"));
                m.setAtivo(rs.getBoolean("ativo"));
                medicos.add(m);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar médico: " + e.getMessage());
        }
        return medicos;
    }

    public int buscarIdPorUsuario(int usuarioId) {
        String sql = "SELECT id FROM medicos WHERE usuario_id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar médico por usuário: " + e.getMessage());
        }
        return -1;
    }
}