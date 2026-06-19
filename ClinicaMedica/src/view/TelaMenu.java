package view;

import model.Usuario;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class TelaMenu extends JFrame {

    private Usuario usuarioLogado;

    private static final Color VERDE_ESCURO = new Color(27, 94, 32);
    private static final Color VERDE_MEDIO = new Color(46, 125, 50);
    private static final Color BRANCO = Color.WHITE;
    private static final Color CINZA_FUNDO = new Color(245, 247, 245);
    private static final Color CINZA_BLOQUEADO = new Color(180, 180, 180);

    public TelaMenu(Usuario usuario) {
        this.usuarioLogado = usuario;

        setTitle("Clínica Médica — Menu Principal");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(CINZA_FUNDO);

        // Cabeçalho
        JPanel cabecalho = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, VERDE_ESCURO, getWidth(), 0, VERDE_MEDIO);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        cabecalho.setPreferredSize(new Dimension(700, 75));
        cabecalho.setLayout(new BorderLayout());
        cabecalho.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));

        JPanel painelTitulo = new JPanel(new GridBagLayout());
        painelTitulo.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel icone = new JLabel("🏥 ");
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        gbc.gridx = 0; gbc.gridy = 0;
        painelTitulo.add(icone, gbc);

        JLabel titulo = new JLabel("Sistema de Gestão de Clínica Médica");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(BRANCO);
        gbc.gridx = 1;
        painelTitulo.add(titulo, gbc);
        cabecalho.add(painelTitulo, BorderLayout.WEST);

        // Info do utilizador
        JPanel painelUser = new JPanel(new GridBagLayout());
        painelUser.setOpaque(false);
        GridBagConstraints gbcU = new GridBagConstraints();
        gbcU.anchor = GridBagConstraints.EAST;

        JLabel labelUser = new JLabel(usuario.getNome());
        labelUser.setFont(new Font("Arial", Font.BOLD, 13));
        labelUser.setForeground(BRANCO);
        gbcU.gridx = 0; gbcU.gridy = 0;
        painelUser.add(labelUser, gbcU);

        JLabel labelPerfil = new JLabel(usuario.getPerfil());
        labelPerfil.setFont(new Font("Arial", Font.PLAIN, 11));
        labelPerfil.setForeground(new Color(200, 230, 200));
        gbcU.gridy = 1;
        painelUser.add(labelPerfil, gbcU);
        cabecalho.add(painelUser, BorderLayout.EAST);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new GridLayout(2, 3, 20, 20));
        painelBotoes.setBackground(CINZA_FUNDO);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        String perfil = usuario.getPerfil();

        // Definir acesso por perfil
        boolean isAdmin = perfil.equals("ADMINISTRADOR");
        boolean isMedico = perfil.equals("MEDICO");
        boolean isRecepcionista = perfil.equals("RECEPCIONISTA");

        boolean verPacientes = isAdmin || isRecepcionista;
        boolean verMedicos = isAdmin;
        boolean verConsultas = isAdmin || isMedico || isRecepcionista;
        boolean verProntuarios = isAdmin || isMedico;
        boolean verRelatorios = isAdmin;

        painelBotoes.add(criarCartao("👥", "Pacientes", "Gerir pacientes da clínica", new Color(25, 118, 210), verPacientes));
        painelBotoes.add(criarCartao("👨‍⚕️", "Médicos", "Gerir corpo clínico", VERDE_MEDIO, verMedicos));
        painelBotoes.add(criarCartao("📅", "Consultas", "Agendar e gerir consultas", new Color(123, 31, 162), verConsultas));
        painelBotoes.add(criarCartao("📋", "Prontuários", "Prontuários electrónicos", new Color(230, 81, 0), verProntuarios));
        painelBotoes.add(criarCartao("📊", "Relatórios", "Relatórios gerenciais", new Color(0, 105, 92), verRelatorios));
        painelBotoes.add(criarCartao("🚪", "Sair", "Terminar sessão", new Color(183, 28, 28), true));

        painelPrincipal.add(cabecalho, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

        add(painelPrincipal);
        setVisible(true);
    }

    private JPanel criarCartao(String icone, String titulo, String descricao,
                               Color cor, boolean activo) {
        Color corFinal = activo ? cor : CINZA_BLOQUEADO;

        JPanel cartao = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        cartao.setBackground(corFinal);
        cartao.setCursor(activo ? new Cursor(Cursor.HAND_CURSOR) : new Cursor(Cursor.DEFAULT_CURSOR));
        cartao.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        JLabel lblIcone = new JLabel(activo ? icone : "🔒");
        lblIcone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        lblIcone.setForeground(BRANCO);
        lblIcone.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0);
        cartao.add(lblIcone, gbc);

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitulo.setForeground(BRANCO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 3, 0);
        cartao.add(lblTitulo, gbc);

        JLabel lblDesc = new JLabel(activo ? descricao : "Sem permissão");
        lblDesc.setFont(new Font("Arial", Font.PLAIN, 10));
        lblDesc.setForeground(new Color(255, 255, 255, 180));
        lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        cartao.add(lblDesc, gbc);

        // Efeito hover e clique apenas se activo
        if (activo) {
            cartao.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    cartao.setBackground(corFinal.darker());
                    cartao.repaint();
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    cartao.setBackground(corFinal);
                    cartao.repaint();
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    abrirTela(titulo);
                }
            });
        } else {
            cartao.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(null,
                            "Não tem permissão para aceder a este módulo.",
                            "Acesso Negado",
                            JOptionPane.WARNING_MESSAGE);
                }
            });
        }

        return cartao;
    }

    private void abrirTela(String titulo) {
        switch (titulo) {
            case "Pacientes" -> new TelaPacientes(usuarioLogado);
            case "Médicos" -> new TelaMedicos(usuarioLogado);
            case "Consultas" -> new TelaConsultas(usuarioLogado);
            case "Prontuários" -> new TelaProntuarios(usuarioLogado);
            case "Relatórios" -> new TelaRelatorios(usuarioLogado);
            case "Sair" -> {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja sair?",
                        "Sair", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) System.exit(0);
            }
        }
    }
}