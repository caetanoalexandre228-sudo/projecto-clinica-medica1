package dao;

import model.Prontuario;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProntuarioDAO {

    // Salvar novo prontuário
    public void salvar(Prontuario prontuario) {
        String sql = "INSERT INTO prontuarios (consulta_id, diagnostico, prescricao, observacoes) VALUES (?, ?, ?, ?)";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, prontuario.getConsultaId());
            stmt.setString(2, prontuario.getDiagnostico());
            stmt.setString(3, prontuario.getPrescricao());
            stmt.setString(4, prontuario.getObservacoes());
            stmt.executeUpdate();
            System.out.println("Prontuário salvo com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar prontuário: " + e.getMessage());
        }
    }

    // Buscar prontuário por consulta
    public Prontuario buscarPorConsulta(int consultaId) {
        String sql = "SELECT * FROM prontuarios WHERE consulta_id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, consultaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Prontuario p = new Prontuario();
                p.setId(rs.getInt("id"));
                p.setConsultaId(rs.getInt("consulta_id"));
                p.setDiagnostico(rs.getString("diagnostico"));
                p.setPrescricao(rs.getString("prescricao"));
                p.setObservacoes(rs.getString("observacoes"));
                p.setDataRegistro(rs.getString("data_registro"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar prontuário: " + e.getMessage());
        }
        return null;
    }

    // Buscar todos os prontuários de um paciente
    public List<Prontuario> buscarPorPaciente(int pacienteId) {
        List<Prontuario> prontuarios = new ArrayList<>();
        String sql = "SELECT p.* FROM prontuarios p " +
                "INNER JOIN consultas c ON p.consulta_id = c.id " +
                "WHERE c.paciente_id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pacienteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Prontuario p = new Prontuario();
                p.setId(rs.getInt("id"));
                p.setConsultaId(rs.getInt("consulta_id"));
                p.setDiagnostico(rs.getString("diagnostico"));
                p.setPrescricao(rs.getString("prescricao"));
                p.setObservacoes(rs.getString("observacoes"));
                p.setDataRegistro(rs.getString("data_registro"));
                prontuarios.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar prontuários: " + e.getMessage());
        }
        return prontuarios;
    }

    // Atualizar prontuário
    public void atualizar(Prontuario prontuario) {
        String sql = "UPDATE prontuarios SET diagnostico=?, prescricao=?, observacoes=? WHERE id=?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, prontuario.getDiagnostico());
            stmt.setString(2, prontuario.getPrescricao());
            stmt.setString(3, prontuario.getObservacoes());
            stmt.setInt(4, prontuario.getId());
            stmt.executeUpdate();
            System.out.println("Prontuário atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar prontuário: " + e.getMessage());
        }
    }
}