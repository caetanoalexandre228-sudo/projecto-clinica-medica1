package controller;

import dao.ConsultaDAO;
import model.Consulta;

import java.util.List;

public class ConsultaController {

    private ConsultaDAO dao;

    public ConsultaController() {
        this.dao = new ConsultaDAO();
    }

    public void salvar(Consulta consulta) {
        dao.salvar(consulta);
    }

    public void atualizarStatus(int id, String status) {
        dao.atualizarStatus(id, status);
    }

    public void cancelar(int id) {
        dao.cancelar(id);
    }

    public List<Consulta> buscarTodos() {
        return dao.buscarTodos();
    }

    public List<Consulta> buscarPorMedico(int medicoId) {
        return dao.buscarPorMedico(medicoId);
    }
}