package view;

import controller.MedicoController;
import model.Medico;

import javax.swing.*;
import java.awt.*;

public class FormularioMedico extends JDialog {

    private JTextField campoNome, campoCrm, campoEspecialidade,
            campoTelefone, campoEmail;
    private MedicoController controller;
    private Medico medico;

    public FormularioMedico(JFrame parent, Medico medico, MedicoController controller) {
        super(parent, true);
        this.controller = controller;
        this.medico = medico;

        setTitle(medico == null ? "Novo Médico" : "Editar Médico");
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setResizable(false);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        campoNome = new JTextField(20);
        campoCrm = new JTextField(20);
        campoEspecialidade = new JTextField(20);
        campoTelefone = new JTextField(20);
        campoEmail = new JTextField(20);

        // Adiciona campos ao painel
        adicionarCampo(painel, gbc, "Nome:", campoNome, 0);
        adicionarCampo(painel, gbc, "CRM:", campoCrm, 1);
        adicionarCampo(painel, gbc, "Especialidade:", campoEspecialidade, 2);
        adicionarCampo(painel, gbc, "Telefone:", campoTelefone, 3);
        adicionarCampo(painel, gbc, "Email:", campoEmail, 4);

        // Botão salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(46, 204, 113));
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        painel.add(btnSalvar, gbc);

        // Se for edição preenche os campos
        if (medico != null) {
            campoNome.setText(medico.getNome());
            campoCrm.setText(medico.getCrm());
            campoEspecialidade.setText(medico.getEspecialidade());
            campoTelefone.setText(medico.getTelefone());
            campoEmail.setText(medico.getEmail());
        }

        // Ação do botão salvar
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
        if (campoNome.getText().isEmpty() || campoCrm.getText().isEmpty()
                || campoEspecialidade.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nome, CRM e Especialidade são obrigatórios!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (medico == null) {
            medico = new Medico();
        }

        medico.setNome(campoNome.getText());
        medico.setCrm(campoCrm.getText());
        medico.setEspecialidade(campoEspecialidade.getText());
        medico.setTelefone(campoTelefone.getText());
        medico.setEmail(campoEmail.getText());
        medico.setAtivo(true);

        if (medico.getId() == 0) {
            controller.salvar(medico);
        } else {
            controller.atualizar(medico);
        }

        JOptionPane.showMessageDialog(this, "Médico salvo com sucesso!");
        dispose();
    }
}