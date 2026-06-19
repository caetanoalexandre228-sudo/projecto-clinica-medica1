package controller;

import dao.MedicoDAO;
import model.Medico;

import java.util.List;

public class MedicoController {

    private MedicoDAO dao;

    public MedicoController() {
        this.dao = new MedicoDAO();
    }

    public void salvar(Medico medico) {
        dao.salvar(medico);
    }

    public void atualizar(Medico medico) {
        dao.atualizar(medico);
    }

    public void inativar(int id) {
        dao.inativar(id);
    }

    public List<Medico> buscarTodos() {
        return dao.buscarTodos();
    }

    public List<Medico> buscar(String termo) {
        if (termo == null || termo.isEmpty()) {
            return dao.buscarTodos();
        }
        return dao.buscarPorNomeOuCrm(termo);
    }

    public Medico buscarPorId(int id) {
        List<Medico> todos = dao.buscarTodos();
        for (Medico m : todos) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }
}