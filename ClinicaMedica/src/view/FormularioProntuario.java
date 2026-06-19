package view;

import controller.ProntuarioController;
import model.Prontuario;

import javax.swing.*;
import java.awt.*;

public class FormularioProntuario extends JDialog {

    private JTextField campoConsultaId;
    private JTextArea campoDiagnostico, campoPrescricao, campoObservacoes;
    private ProntuarioController controller;

    public FormularioProntuario(JFrame parent, ProntuarioController controller) {
        super(parent, true);
        this.controller = controller;

        setTitle("Novo Prontuário");
        setSize(450, 400);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoConsultaId = new JTextField(20);
        campoDiagnostico = new JTextArea(3, 20);
        campoDiagnostico.setLineWrap(true);
        campoPrescricao = new JTextArea(3, 20);
        campoPrescricao.setLineWrap(true);
        campoObservacoes = new JTextArea(3, 20);
        campoObservacoes.setLineWrap(true);

        // Adiciona campos
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        painel.add(new JLabel("ID da Consulta:"), gbc);
        gbc.gridx = 1;
        painel.add(campoConsultaId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Diagnóstico:"), gbc);
        gbc.gridx = 1;
        painel.add(new JScrollPane(campoDiagnostico), gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Prescrição:"), gbc);
        gbc.gridx = 1;
        painel.add(new JScrollPane(campoPrescricao), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(new JLabel("Observações:"), gbc);
        gbc.gridx = 1;
        painel.add(new JScrollPane(campoObservacoes), gbc);

        // Botão salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        painel.add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> salvar());

        add(painel);
        setVisible(true);
    }

    private void salvar() {
        if (campoConsultaId.getText().isEmpty() || campoDiagnostico.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "ID da Consulta e Diagnóstico são obrigatórios!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Prontuario prontuario = new Prontuario();
            prontuario.setConsultaId(Integer.parseInt(campoConsultaId.getText()));
            prontuario.setDiagnostico(campoDiagnostico.getText());
            prontuario.setPrescricao(campoPrescricao.getText());
            prontuario.setObservacoes(campoObservacoes.getText());

            controller.salvar(prontuario);
            JOptionPane.showMessageDialog(this, "Prontuário salvo com sucesso!");
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "ID da Consulta deve ser um número!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}