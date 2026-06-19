package view;

import controller.ConsultaController;
import controller.MedicoController;
import controller.PacienteController;
import model.Consulta;
import model.Medico;
import model.Paciente;
import model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaRelatorios extends JFrame {

    private ConsultaController consultaController;
    private PacienteController pacienteController;
    private MedicoController medicoController;
    private Usuario usuarioLogado;

    private static final Color VERDE_ESCURO = new Color(27, 94, 32);
    private static final Color VERDE_MEDIO = new Color(46, 125, 50);
    private static final Color BRANCO = Color.WHITE;
    private static final Color CINZA_FUNDO = new Color(245, 247, 245);

    public TelaRelatorios(Usuario usuario) {
        this.usuarioLogado = usuario;
        this.consultaController = new ConsultaController();
        this.pacienteController = new PacienteController();
        this.medicoController = new MedicoController();

        setTitle("Relatórios");
        setSize(900, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(CINZA_FUNDO);

        // Cabeçalho
        JPanel cabecalho = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, VERDE_ESCURO,
                        getWidth(), 0, VERDE_MEDIO);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        cabecalho.setPreferredSize(new Dimension(900, 60));
        cabecalho.setLayout(new BorderLayout());
        cabecalho.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel titulo = new JLabel("📊  Relatórios Gerenciais");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(BRANCO);
        cabecalho.add(titulo, BorderLayout.WEST);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelBotoes.setBackground(BRANCO);
        painelBotoes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JButton btnPacientes = criarBotao("👥 Pacientes Ativos", new Color(25, 118, 210));
        JButton btnMedicos = criarBotao("👨‍⚕️ Médicos Ativos", VERDE_MEDIO);
        JButton btnConsultas = criarBotao("📅 Todas as Consultas", new Color(123, 31, 162));
        JButton btnPorMedico = criarBotao("🔍 Consultas por Médico", new Color(230, 81, 0));

        painelBotoes.add(btnPacientes);
        painelBotoes.add(btnMedicos);
        painelBotoes.add(btnConsultas);
        painelBotoes.add(btnPorMedico);

        // Área de resultado
        JTextArea areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaResultado.setBackground(BRANCO);
        areaResultado.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        areaResultado.setForeground(new Color(33, 33, 33));

        JScrollPane scroll = new JScrollPane(areaResultado);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        scroll.getViewport().setBackground(BRANCO);

        // Ações
        btnPacientes.addActionListener(e -> {
            List<Paciente> pacientes = pacienteController.buscarTodos();
            StringBuilder sb = new StringBuilder();
            sb.append("╔══════════════════════════════════════════════════════════╗\n");
            sb.append("║           RELATÓRIO DE PACIENTES ATIVOS                 ║\n");
            sb.append("╚══════════════════════════════════════════════════════════╝\n\n");
            sb.append(String.format("%-5s %-30s %-18s %-15s\n",
                    "ID", "Nome", "BI", "Telefone"));
            sb.append("─".repeat(70)).append("\n");
            for (Paciente p : pacientes) {
                sb.append(String.format("%-5d %-30s %-18s %-15s\n",
                        p.getId(), p.getNome(), p.getBi(), p.getTelefone()));
            }
            sb.append("\n").append("─".repeat(70)).append("\n");
            sb.append("Total: ").append(pacientes.size()).append(" pacientes activos\n");
            areaResultado.setText(sb.toString());
        });

        btnMedicos.addActionListener(e -> {
            List<Medico> medicos = medicoController.buscarTodos();
            StringBuilder sb = new StringBuilder();
            sb.append("╔══════════════════════════════════════════════════════════╗\n");
            sb.append("║             RELATÓRIO DE MÉDICOS ATIVOS                 ║\n");
            sb.append("╚══════════════════════════════════════════════════════════╝\n\n");
            sb.append(String.format("%-5s %-30s %-15s %-20s\n",
                    "ID", "Nome", "CRM", "Especialidade"));
            sb.append("─".repeat(70)).append("\n");
            for (Medico m : medicos) {
                sb.append(String.format("%-5d %-30s %-15s %-20s\n",
                        m.getId(), m.getNome(), m.getCrm(), m.getEspecialidade()));
            }
            sb.append("\n").append("─".repeat(70)).append("\n");
            sb.append("Total: ").append(medicos.size()).append(" médicos activos\n");
            areaResultado.setText(sb.toString());
        });

        btnConsultas.addActionListener(e -> {
            List<Consulta> consultas = consultaController.buscarTodos();
            StringBuilder sb = new StringBuilder();
            sb.append("╔══════════════════════════════════════════════════════════╗\n");
            sb.append("║            RELATÓRIO DE TODAS AS CONSULTAS              ║\n");
            sb.append("╚══════════════════════════════════════════════════════════╝\n\n");
            sb.append(String.format("%-5s %-12s %-12s %-22s %-15s\n",
                    "ID", "Paciente", "Médico", "Data/Hora", "Status"));
            sb.append("─".repeat(70)).append("\n");
            for (Consulta c : consultas) {
                sb.append(String.format("%-5d %-12d %-12d %-22s %-15s\n",
                        c.getId(), c.getPacienteId(), c.getMedicoId(),
                        c.getDataHora(), c.getStatus()));
            }
            sb.append("\n").append("─".repeat(70)).append("\n");
            sb.append("Total: ").append(consultas.size()).append(" consultas\n");
            areaResultado.setText(sb.toString());
        });

        btnPorMedico.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Digite o ID do médico:");
            if (input != null && !input.isEmpty()) {
                try {
                    int medicoId = Integer.parseInt(input);
                    List<Consulta> consultas = consultaController.buscarPorMedico(medicoId);
                    StringBuilder sb = new StringBuilder();
                    sb.append("╔══════════════════════════════════════════════════════════╗\n");
                    sb.append("║         CONSULTAS DO MÉDICO ID: ").append(medicoId).append("                       ║\n");
                    sb.append("╚══════════════════════════════════════════════════════════╝\n\n");
                    sb.append(String.format("%-5s %-12s %-22s %-15s\n",
                            "ID", "Paciente", "Data/Hora", "Status"));
                    sb.append("─".repeat(55)).append("\n");
                    for (Consulta c : consultas) {
                        sb.append(String.format("%-5d %-12d %-22s %-15s\n",
                                c.getId(), c.getPacienteId(),
                                c.getDataHora(), c.getStatus()));
                    }
                    sb.append("\n").append("─".repeat(55)).append("\n");
                    sb.append("Total: ").append(consultas.size()).append(" consultas\n");
                    areaResultado.setText(sb.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Digite um ID válido!");
                }
            }
        });

        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(CINZA_FUNDO);
        painelCentro.add(painelBotoes, BorderLayout.NORTH);
        painelCentro.add(scroll, BorderLayout.CENTER);

        painelPrincipal.add(cabecalho, BorderLayout.NORTH);
        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        add(painelPrincipal);
        setVisible(true);
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setBackground(cor);
        btn.setForeground(BRANCO);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(7, 14, 7, 14));
        return btn;
    }
}