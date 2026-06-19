package view;

import controller.ConsultaController;
import model.Consulta;
import model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import dao.MedicoDAO;

public class TelaConsultas extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ConsultaController controller;
    private Usuario usuarioLogado;

    private static final Color VERDE_ESCURO = new Color(27, 94, 32);
    private static final Color VERDE_MEDIO = new Color(46, 125, 50);
    private static final Color BRANCO = Color.WHITE;
    private static final Color CINZA_FUNDO = new Color(245, 247, 245);

    public TelaConsultas(Usuario usuario) {
        this.usuarioLogado = usuario;
        this.controller = new ConsultaController();

        setTitle("Gestão de Consultas");
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

        JLabel titulo = new JLabel("📅  Gestão de Consultas");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(BRANCO);
        cabecalho.add(titulo, BorderLayout.WEST);

        // Painel de ferramentas
        JPanel painelFerramentas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelFerramentas.setBackground(BRANCO);
        painelFerramentas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JButton btnNova = criarBotao("✚ Nova Consulta", VERDE_MEDIO);
        JButton btnConfirmar = criarBotao("✔ Confirmar", new Color(25, 118, 210));
        JButton btnRealizar = criarBotao("✅ Realizar", new Color(0, 105, 92));
        JButton btnCancelar = criarBotao("✖ Cancelar", new Color(183, 28, 28));

        painelFerramentas.add(btnNova);
        painelFerramentas.add(btnConfirmar);
        painelFerramentas.add(btnRealizar);
        painelFerramentas.add(btnCancelar);

        // Tabela
        String[] colunas = {"ID", "Paciente", "Médico", "Data/Hora", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
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
        btnNova.addActionListener(e -> {
            new FormularioConsulta(this, null, controller);
            carregarTabela();
        });
        btnConfirmar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma consulta!");
                return;
            }
            controller.atualizarStatus((int) modeloTabela.getValueAt(linha, 0), "CONFIRMADA");
            carregarTabela();
        });
        btnRealizar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma consulta!");
                return;
            }
            controller.atualizarStatus((int) modeloTabela.getValueAt(linha, 0), "REALIZADA");
            carregarTabela();
        });
        btnCancelar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma consulta!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Cancelar esta consulta?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.cancelar((int) modeloTabela.getValueAt(linha, 0));
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

        List<Consulta> consultas;

        if (usuarioLogado.getPerfil().equals("MEDICO")) {
            MedicoDAO medicoDAO = new MedicoDAO();
            int medicoId = medicoDAO.buscarIdPorUsuario(usuarioLogado.getId());
            consultas = controller.buscarPorMedico(medicoId);
        } else {
            consultas = controller.buscarTodos();
        }

        for (Consulta c : consultas) {
            modeloTabela.addRow(new Object[]{
                    c.getId(), c.getPacienteNome(), c.getMedicoNome(),
                    c.getDataHora(), c.getStatus()
            });
        }
        }
    }

