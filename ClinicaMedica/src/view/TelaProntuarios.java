package view;

import controller.ProntuarioController;
import model.Prontuario;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class TelaProntuarios extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoBusca;
    private ProntuarioController controller;
    private Usuario usuarioLogado;

    private static final Color VERDE_ESCURO = new Color(27, 94, 32);
    private static final Color VERDE_MEDIO = new Color(46, 125, 50);
    private static final Color BRANCO = Color.WHITE;
    private static final Color CINZA_FUNDO = new Color(245, 247, 245);

    public TelaProntuarios(Usuario usuario) {
        this.usuarioLogado = usuario;
        this.controller = new ProntuarioController();

        setTitle("Gestão de Prontuários");
        setSize(900, 550);
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

        JLabel titulo = new JLabel("📋  Gestão de Prontuários");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(BRANCO);
        cabecalho.add(titulo, BorderLayout.WEST);

        // Painel de ferramentas
        JPanel painelFerramentas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelFerramentas.setBackground(BRANCO);
        painelFerramentas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JLabel lblBusca = new JLabel("ID do Paciente:");
        lblBusca.setFont(new Font("Arial", Font.BOLD, 12));
        lblBusca.setForeground(VERDE_ESCURO);

        campoBusca = new JTextField(10);
        campoBusca.setFont(new Font("Arial", Font.PLAIN, 13));
        campoBusca.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 230, 201)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));

        JButton btnBuscar = criarBotao("🔍 Buscar", new Color(25, 118, 210));
        JButton btnNovo = criarBotao("✚ Novo Prontuário", VERDE_MEDIO);
        JButton btnVer = criarBotao("👁 Ver Detalhes", new Color(123, 31, 162));

        painelFerramentas.add(lblBusca);
        painelFerramentas.add(campoBusca);
        painelFerramentas.add(btnBuscar);
        painelFerramentas.add(Box.createHorizontalStrut(10));
        painelFerramentas.add(btnNovo);
        painelFerramentas.add(btnVer);

        // Tabela
        String[] colunas = {"ID", "Consulta ID", "Diagnóstico", "Prescrição", "Data Registro"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Arial", Font.PLAIN, 13));
        tabela.setRowHeight(30);
        tabela.setSelectionBackground(new Color(200, 230, 201));
        tabela.setSelectionForeground(VERDE_ESCURO);
        tabela.setGridColor(new Color(230, 230, 230));
        tabela.setShowVerticalLines(false);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = tabela.getTableHeader();
        header.setBackground(VERDE_ESCURO);
        header.setForeground(BRANCO);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 35));

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        scroll.getViewport().setBackground(BRANCO);

        // Ações
        btnBuscar.addActionListener(e -> {
            try {
                int pacienteId = Integer.parseInt(campoBusca.getText());
                modeloTabela.setRowCount(0);
                List<Prontuario> prontuarios = controller.buscarPorPaciente(pacienteId);
                for (Prontuario p : prontuarios) {
                    modeloTabela.addRow(new Object[]{
                            p.getId(), p.getConsultaId(),
                            p.getDiagnostico(), p.getPrescricao(),
                            p.getDataRegistro()
                    });
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um ID válido!");
            }
        });

        btnNovo.addActionListener(e -> {
            new FormularioProntuario(this, controller);
            if (!campoBusca.getText().isEmpty()) {
                btnBuscar.doClick();
            }
        });

        btnVer.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um prontuário!");
                return;
            }
            String diagnostico = (String) modeloTabela.getValueAt(linha, 2);
            String prescricao = (String) modeloTabela.getValueAt(linha, 3);
            JOptionPane.showMessageDialog(this,
                    "Diagnóstico:\n" + diagnostico + "\n\nPrescrição:\n" + prescricao,
                    "Detalhes do Prontuário",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(CINZA_FUNDO);
        painelCentro.add(painelFerramentas, BorderLayout.NORTH);
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