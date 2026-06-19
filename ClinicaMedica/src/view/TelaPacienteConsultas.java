package view;

import dao.ConsultaDAO;
import dao.PacienteDAO;
import model.Consulta;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class TelaPacienteConsultas extends JFrame {

    private static final Color VERDE_ESCURO = new Color(27, 94, 32);
    private static final Color VERDE_MEDIO = new Color(46, 125, 50);
    private static final Color BRANCO = Color.WHITE;
    private static final Color CINZA_FUNDO = new Color(245, 247, 245);

    public TelaPacienteConsultas(Usuario usuario) {

        setTitle("As Minhas Consultas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        cabecalho.setPreferredSize(new Dimension(800, 70));
        cabecalho.setLayout(new BorderLayout());
        cabecalho.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JLabel titulo = new JLabel("📋  As Minhas Consultas");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(BRANCO);
        cabecalho.add(titulo, BorderLayout.WEST);

        JLabel labelUser = new JLabel("👤 " + usuario.getNome());
        labelUser.setFont(new Font("Arial", Font.BOLD, 13));
        labelUser.setForeground(BRANCO);
        cabecalho.add(labelUser, BorderLayout.EAST);

        // Tabela
        String[] colunas = {"ID", "Médico ID", "Data/Hora", "Status", "Observações"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable tabela = new JTable(modeloTabela);
        tabela.setFont(new Font("Arial", Font.PLAIN, 13));
        tabela.setRowHeight(30);
        tabela.setSelectionBackground(new Color(200, 230, 201));
        tabela.setSelectionForeground(VERDE_ESCURO);
        tabela.setGridColor(new Color(230, 230, 230));
        tabela.setShowVerticalLines(false);

        JTableHeader header = tabela.getTableHeader();
        header.setBackground(VERDE_ESCURO);
        header.setForeground(BRANCO);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(0, 35));

        // Botão sair
        JButton btnSair = new JButton("🚪 Sair");
        btnSair.setBackground(new Color(183, 28, 28));
        btnSair.setForeground(BRANCO);
        btnSair.setFont(new Font("Arial", Font.BOLD, 12));
        btnSair.setFocusPainted(false);
        btnSair.setBorderPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.addActionListener(e -> System.exit(0));

        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelRodape.setBackground(CINZA_FUNDO);
        painelRodape.add(btnSair);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        scroll.getViewport().setBackground(BRANCO);

        painelPrincipal.add(cabecalho, BorderLayout.NORTH);
        painelPrincipal.add(scroll, BorderLayout.CENTER);
        painelPrincipal.add(painelRodape, BorderLayout.SOUTH);

        add(painelPrincipal);

        // Carregar consultas do paciente
        PacienteDAO pacienteDAO = new PacienteDAO();
        int pacienteId = pacienteDAO.buscarIdPorUsuario(usuario.getId());

        ConsultaDAO consultaDAO = new ConsultaDAO();
        List<Consulta> consultas = consultaDAO.buscarPorPaciente(pacienteId);

        for (Consulta c : consultas) {
            modeloTabela.addRow(new Object[]{
                    c.getId(), c.getMedicoNome(),
                    c.getDataHora(), c.getStatus(), c.getObservacoes()
            });
        }

        setVisible(true);
    }
}