package controller;

import dao.UsuarioDAO;
import model.Usuario;
import view.TelaLogin;
import view.TelaMenu;

import javax.swing.*;
import view.TelaPacienteConsultas;

public class LoginController {

    public void autenticar(String login, String senha, TelaLogin tela) {

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(tela,
                    "Preencha o login e a senha!",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.autenticar(login, senha);

        if (usuario != null) {
            JOptionPane.showMessageDialog(tela,
                    "Bem-vindo, " + usuario.getNome() + "!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            tela.dispose();
            if (usuario.getPerfil().equals("PACIENTE")) {
                new TelaPacienteConsultas(usuario);
            } else {
                new TelaMenu(usuario);
            }
        } else {
            JOptionPane.showMessageDialog(tela,
                    "Login ou senha incorretos!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}