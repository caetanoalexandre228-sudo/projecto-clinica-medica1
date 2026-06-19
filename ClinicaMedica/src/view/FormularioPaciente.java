package view;

import controller.PacienteController;
import model.Paciente;

import javax.swing.*;
import java.awt.*;

public class FormularioPaciente extends JDialog {

    private JTextField campoNome, campoBi, campoDataNasc,
            campoTelefone, campoEmail, campoEndereco;
    private JTextArea campoHistorico;
    private Paciente paciente;
    private PacienteController controller;

    public FormularioPaciente(JFrame parent, Paciente paciente, PacienteController controller) {
        super(parent, true);
        this.paciente = paciente;
        this.controller = controller;

        setTitle(paciente == null ? "Novo Paciente" : "Editar Paciente");
        setSize(450, 500);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoNome = new JTextField(20);
        campoBi = new JTextField(20);
        campoDataNasc = new JTextField(20);
        campoTelefone = new JTextField(20);
        campoEmail = new JTextField(20);
        campoEndereco = new JTextField(20);
        campoHistorico = new JTextArea(3, 20);
        campoHistorico.setLineWrap(true);

        adicionarCampo(painel, gbc, "Nome:", campoNome, 0);
        adicionarCampo(painel, gbc, "BI:", campoBi, 1);
        adicionarCampo(painel, gbc, "Data Nasc. (AAAA-MM-DD):", campoDataNasc, 2);
        adicionarCampo(painel, gbc, "Telefone:", campoTelefone, 3);
        adicionarCampo(painel, gbc, "Email:", campoEmail, 4);
        adicionarCampo(painel, gbc, "Endereço:", campoEndereco, 5);

        gbc.gridx = 0; gbc.gridy = 6;
        painel.add(new JLabel("Histórico Médico:"), gbc);
        gbc.gridx = 1; gbc.gridy = 6;
        painel.add(new JScrollPane(campoHistorico), gbc);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        painel.add(btnSalvar, gbc);

        if (paciente != null) {
            campoNome.setText(paciente.getNome());
            campoBi.setText(paciente.getBi());
            campoDataNasc.setText(paciente.getDataNascimento());
            campoTelefone.setText(paciente.getTelefone());
            campoEmail.setText(paciente.getEmail());
            campoEndereco.setText(paciente.getEndereco());
            campoHistorico.setText(paciente.getHistoricoMedico());
        }

        btnSalvar.addActionListener(e -> salvar());

        add(painel);
        setVisible(true);
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc,
                                String label, JTextField campo, int linha) {
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 1;
        painel.add(new JLabel(label), gbc);
        gbc.gridx = 1; gbc.gridy = linha;
        painel.add(campo, gbc);
    }

    private void salvar() {
        if (campoNome.getText().isEmpty() || campoBi.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e BI são obrigatórios!");
            return;
        }

        Paciente p = paciente == null ? new Paciente() : paciente;
        p.setNome(campoNome.getText());
        p.setBi(campoBi.getText());
        p.setDataNascimento(campoDataNasc.getText());
        p.setTelefone(campoTelefone.getText());
        p.setEmail(campoEmail.getText());
        p.setEndereco(campoEndereco.getText());
        p.setHistoricoMedico(campoHistorico.getText());
        p.setAtivo(true);

        if (paciente == null) {
            controller.salvar(p);
        } else {
            controller.atualizar(p);
        }

        JOptionPane.showMessageDialog(this, "Paciente salvo com sucesso!");
        dispose();
    }
}