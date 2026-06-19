package view;

import controller.ConsultaController;
import controller.MedicoController;
import controller.PacienteController;
import model.Consulta;
import model.Medico;
import model.Paciente;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormularioConsulta extends JDialog {

    private JComboBox<String> comboPaciente, comboMedico;
    private JTextField campoDataHora;
    private JTextArea campoObservacoes;
    private ConsultaController controller;
    private List<Paciente> pacientes;
    private List<Medico> medicos;

    public FormularioConsulta(JFrame parent, Consulta consulta, ConsultaController controller) {
        super(parent, true);
        this.controller = controller;

        setTitle("Nova Consulta");
        setSize(550, 450);
        setLocationRelativeTo(parent);
        setResizable(true);

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Carrega pacientes e médicos
        pacientes = new PacienteController().buscarTodos();
        medicos = new MedicoController().buscarTodos();

        // ComboBox de pacientes
        comboPaciente = new JComboBox<>();
        for (Paciente p : pacientes) {
            comboPaciente.addItem(p.getNome() + " - " + p.getBi());
        }

        // ComboBox de médicos
        comboMedico = new JComboBox<>();
        for (Medico m : medicos) {
            comboMedico.addItem(m.getNome() + " - " + m.getEspecialidade());
        }

        campoDataHora = new JTextField(20);
        campoObservacoes = new JTextArea(3, 20);
        campoObservacoes.setLineWrap(true);

        // Adiciona campos
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        painel.add(new JLabel("Paciente:"), gbc);
        gbc.gridx = 1;
        painel.add(comboPaciente, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Médico:"), gbc);
        gbc.gridx = 1;
        painel.add(comboMedico, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Data/Hora (AAAA-MM-DD HH:MM):"), gbc);
        gbc.gridx = 1;
        painel.add(campoDataHora, gbc);

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
        if (campoDataHora.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Data e hora são obrigatórios!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int indicePaciente = comboPaciente.getSelectedIndex();
        int indiceMedico = comboMedico.getSelectedIndex();

        if (indicePaciente == -1 || indiceMedico == -1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um paciente e um médico!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Consulta consulta = new Consulta();
        consulta.setPacienteId(pacientes.get(indicePaciente).getId());
        consulta.setMedicoId(medicos.get(indiceMedico).getId());
        consulta.setDataHora(campoDataHora.getText());
        consulta.setStatus("AGENDADA");
        consulta.setObservacoes(campoObservacoes.getText());

        controller.salvar(consulta);
        JOptionPane.showMessageDialog(this, "Consulta agendada com sucesso!");
        dispose();
    }
}