package controller;

import dao.ProntuarioDAO;
import model.Prontuario;

import java.util.List;

public class ProntuarioController {

    private ProntuarioDAO dao;

    public ProntuarioController() {
        this.dao = new ProntuarioDAO();
    }

    public void salvar(Prontuario prontuario) {
        dao.salvar(prontuario);
    }

    public void atualizar(Prontuario prontuario) {
        dao.atualizar(prontuario);
    }

    public Prontuario buscarPorConsulta(int consultaId) {
        return dao.buscarPorConsulta(consultaId);
    }

    public List<Prontuario> buscarPorPaciente(int pacienteId) {
        return dao.buscarPorPaciente(pacienteId);
    }
}