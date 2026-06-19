package view;

import controller.MedicoController;
import model.Medico;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class TelaMedicos extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoBusca;
    private MedicoController controller;
    private Usuario usuarioLogado;

    private static final Color VERDE_ESCURO = new Color(27, 94, 32);
    private static final Color VERDE_MEDIO = new Color(46, 125, 50);
    private static final Color BRANCO = Color.WHITE;
    private static final Color CINZA_FUNDO = new Color(245, 247, 245);

    public TelaMedicos(Usuario usuario) {
        this.usuarioLogado = usuario;
        this.controller = new MedicoController();

        setTitle("Gestão de Médicos");
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

        JLabel titulo = new JLabel("👨‍⚕️  Gestão de Médicos");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(BRANCO);
        cabecalho.add(titulo, BorderLayout.WEST);

        // Painel de ferramentas
        JPanel painelFerramentas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelFerramentas.setBackground(BRANCO);
        painelFerramentas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JLabel lblBusca = new JLabel("Buscar:");
        lblBusca.setFont(new Font("Arial", Font.BOLD, 12));
        lblBusca.setForeground(VERDE_ESCURO);

        campoBusca = new JTextField(20);
        campoBusca.setFont(new Font("Arial", Font.PLAIN, 13));
        campoBusca.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 230, 201)),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)));

        JButton btnBuscar = criarBotao("🔍 Buscar", new Color(25, 118, 210));
        JButton btnNovo = criarBotao("✚ Novo Médico", VERDE_MEDIO);
        JButton btnEditar = criarBotao("✎ Editar", new Color(230, 81, 0));
        JButton btnInativar = criarBotao("✖ Inativar", new Color(183, 28, 28));

        painelFerramentas.add(lblBusca);
        painelFerramentas.add(campoBusca);
        painelFerramentas.add(btnBuscar);
        painelFerramentas.add(Box.createHorizontalStrut(10));
        painelFerramentas.add(btnNovo);
        painelFerramentas.add(btnEditar);
        painelFerramentas.add(btnInativar);

        // Tabela
        String[] colunas = {"ID", "Nome", "CRM", "Especialidade", "Telefone", "Email"};
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

        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        scroll.getViewport().setBackground(BRANCO);

        // Ações
        btnBuscar.addActionListener(e -> buscar());
        campoBusca.addActionListener(e -> buscar());
        btnNovo.addActionListener(e -> abrirFormulario(null));
        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um médico!");
                return;
            }
            int id = (int) modeloTabela.getValueAt(linha, 0);
            abrirFormulario(controller.buscarPorId(id));
        });
        btnInativar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um médico!");
                return;
            }
            int id = (int) modeloTabela.getValueAt(linha, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja inativar este médico?",
                    "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.inativar(id);
                carregarTabela();
            }
        });

        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(CINZA_FUNDO);
        painelCentro.add(painelFerramentas, BorderLayout.NORTH);
        painelCentro.add(scroll, BorderLayout.CENTER);

        painelPrincipal.add(cabecalho, BorderLayout.NORTH);
        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        add(painelPrincipal);
        carregarTabela();
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

    private void carregarTabela() {
        modeloTabela.setRowCount(0);
        List<Medico> medicos = controller.buscarTodos();
        for (Medico m : medicos) {
            modeloTabela.addRow(new Object[]{
                    m.getId(), m.getNome(), m.getCrm(),
                    m.getEspecialidade(), m.getTelefone(), m.getEmail()
            });
        }
    }

    private void buscar() {
        String termo = campoBusca.getText();
        modeloTabela.setRowCount(0);
        List<Medico> medicos = controller.buscar(termo);
        for (Medico m : medicos) {
            modeloTabela.addRow(new Object[]{
                    m.getId(), m.getNome(), m.getCrm(),
                    m.getEspecialidade(), m.getTelefone(), m.getEmail()
            });
        }
    }

    private void abrirFormulario(Medico medico) {
        new FormularioMedico(this, medico, controller);
        carregarTabela();
    }
}