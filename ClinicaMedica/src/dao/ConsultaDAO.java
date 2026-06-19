package dao;

import model.Consulta;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    // Salvar nova consulta
    public void salvar(Consulta consulta) {
        String sql = "INSERT INTO consultas (paciente_id, medico_id, data_hora, status, observacoes) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, consulta.getPacienteId());
            stmt.setInt(2, consulta.getMedicoId());
            stmt.setString(3, consulta.getDataHora());
            stmt.setString(4, consulta.getStatus());
            stmt.setString(5, consulta.getObservacoes());
            stmt.executeUpdate();
            System.out.println("Consulta salva com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar consulta: " + e.getMessage());
        }
    }

    // Buscar todas as consultas
    public List<Consulta> buscarTodos() {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT c.id, p.nome AS paciente_nome, m.nome AS medico_nome, " +
                "c.data_hora, c.status, c.observacoes " +
                "FROM consultas c " +
                "JOIN pacientes p ON p.id = c.paciente_id " +
                "JOIN medicos m ON m.id = c.medico_id";
        try {
            Connection conn = Conexao.getConexao();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));
                c.setPacienteId(rs.getInt("paciente_id"));
                c.setMedicoId(rs.getInt("medico_id"));
                c.setDataHora(rs.getString("data_hora"));
                c.setStatus(rs.getString("status"));
                c.setObservacoes(rs.getString("observacoes"));
                consultas.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar consultas: " + e.getMessage());
        }
        return consultas;
    }

    // Atualizar status da consulta
    public void atualizarStatus(int id, String status) {
        String sql = "UPDATE consultas SET status = ? WHERE id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Status atualizado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar status: " + e.getMessage());
        }
    }

    // Cancelar consulta
    public void cancelar(int id) {
        String sql = "UPDATE consultas SET status = 'CANCELADA' WHERE id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Consulta cancelada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cancelar consulta: " + e.getMessage());
        }
    }

    // Buscar consultas por médico
    public List<Consulta> buscarPorMedico(int medicoId) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT c.id, p.nome AS paciente_nome, m.nome AS medico_nome, " +
                "c.data_hora, c.status, c.observacoes " +
                "FROM consultas c " +
                "JOIN pacientes p ON p.id = c.paciente_id " +
                "JOIN medicos m ON m.id = c.medico_id " +
                "WHERE c.medico_id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, medicoId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));
                c.setPacienteNome(rs.getString("paciente_nome"));
                c.setMedicoNome(rs.getString("medico_nome"));
                c.setDataHora(rs.getString("data_hora"));
                c.setStatus(rs.getString("status"));
                c.setObservacoes(rs.getString("observacoes"));
                consultas.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar consultas: " + e.getMessage());
        }
        return consultas;
    }
    public List<Consulta> buscarPorPaciente(int pacienteId) {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT c.id, c.paciente_id, m.nome AS medico_nome, c.data_hora, c.status, c.observacoes " +
                "FROM consultas c " +
                "JOIN medicos m ON m.id = c.medico_id " +
                "WHERE c.paciente_id = ?";
        try {
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pacienteId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Consulta c = new Consulta();
                c.setId(rs.getInt("id"));
                c.setPacienteNome(rs.getString("paciente_nome"));
                c.setMedicoNome(rs.getString("medico_nome"));
                c.setDataHora(rs.getString("data_hora"));
                c.setStatus(rs.getString("status"));
                c.setObservacoes(rs.getString("observacoes"));
                consultas.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar consultas do paciente: " + e.getMessage());
        }
        return consultas;
    }
}