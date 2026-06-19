package controller;

import dao.PacienteDAO;
import model.Paciente;

import java.util.List;

public class PacienteController {

    private PacienteDAO dao;

    public PacienteController() {
        this.dao = new PacienteDAO();
    }

    public void salvar(Paciente paciente) {
        dao.salvar(paciente);
    }

    public void atualizar(Paciente paciente) {
        dao.atualizar(paciente);
    }

    public void inativar(int id) {
        dao.inativar(id);
    }

    public List<Paciente> buscarTodos() {
        return dao.buscarTodos();
    }

    public List<Paciente> buscar(String termo) {
        if (termo == null || termo.isEmpty()) {
            return dao.buscarTodos();
        }
        return dao.buscarPorNomeOuBi(termo);
    }

    public Paciente buscarPorId(int id) {
        List<Paciente> todos = dao.buscarTodos();
        for (Paciente p : todos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}