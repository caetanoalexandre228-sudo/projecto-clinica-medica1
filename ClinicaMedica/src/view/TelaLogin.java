package view;

import controller.LoginController;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.geom.*;

public class TelaLogin extends JFrame {

    private JTextField campoLogin;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;

    // Cores do sistema
    static final Color VERDE_ESCURO = new Color(27, 94, 32);
    static final Color VERDE_MEDIO = new Color(46, 125, 50);
    static final Color VERDE_CLARO = new Color(76, 175, 80);
    static final Color BRANCO = Color.WHITE;
    static final Color CINZA_CLARO = new Color(245, 245, 245);
    static final Color CINZA_TEXTO = new Color(97, 97, 97);

    public TelaLogin() {
        setTitle("Sistema de Gestão de Clínica Médica");
        setSize(580, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout());

        // Painel esquerdo
        JPanel painelEsquerdo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, VERDE_ESCURO,
                        0, getHeight(), VERDE_CLARO);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        painelEsquerdo.setPreferredSize(new Dimension(200, 420));
        painelEsquerdo.setLayout(new GridBagLayout());

        JPanel conteudoEsquerdo = new JPanel();
        conteudoEsquerdo.setOpaque(false);
        conteudoEsquerdo.setLayout(new BoxLayout(conteudoEsquerdo, BoxLayout.Y_AXIS));

        JLabel icone = new JLabel("🏥");
        icone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 55));
        icone.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nomeSistema = new JLabel("CLÍNICA");
        nomeSistema.setFont(new Font("Arial", Font.BOLD, 20));
        nomeSistema.setForeground(BRANCO);
        nomeSistema.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nomeSubtitulo = new JLabel("MÉDICA");
        nomeSubtitulo.setFont(new Font("Arial", Font.BOLD, 20));
        nomeSubtitulo.setForeground(BRANCO);
        nomeSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 80));
        sep.setMaximumSize(new Dimension(120, 1));

        JLabel versao = new JLabel("v1.0");
        versao.setFont(new Font("Arial", Font.PLAIN, 11));
        versao.setForeground(new Color(255, 255, 255, 150));
        versao.setAlignmentX(Component.CENTER_ALIGNMENT);

        conteudoEsquerdo.add(Box.createVerticalStrut(20));
        conteudoEsquerdo.add(icone);
        conteudoEsquerdo.add(Box.createVerticalStrut(15));
        conteudoEsquerdo.add(nomeSistema);
        conteudoEsquerdo.add(nomeSubtitulo);
        conteudoEsquerdo.add(Box.createVerticalStrut(10));
        conteudoEsquerdo.add(sep);
        conteudoEsquerdo.add(Box.createVerticalStrut(8));
        conteudoEsquerdo.add(versao);

        painelEsquerdo.add(conteudoEsquerdo);

        // Painel direito
        JPanel painelDireito = new JPanel(new GridBagLayout());
        painelDireito.setBackground(BRANCO);
        painelDireito.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Título
        JLabel titulo = new JLabel("Bem-vindo!");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(VERDE_ESCURO);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 4, 0);
        painelDireito.add(titulo, gbc);

        JLabel subtitulo = new JLabel("Aceda ao sistema com as suas credenciais");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 11));
        subtitulo.setForeground(CINZA_TEXTO);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 25, 0);
        painelDireito.add(subtitulo, gbc);

        // Campo login
        JLabel labelLogin = new JLabel("UTILIZADOR");
        labelLogin.setFont(new Font("Arial", Font.BOLD, 11));
        labelLogin.setForeground(VERDE_ESCURO);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 4, 0);
        painelDireito.add(labelLogin, gbc);

        campoLogin = new JTextField();
        campoLogin.setFont(new Font("Arial", Font.PLAIN, 14));
        campoLogin.setPreferredSize(new Dimension(280, 38));
        campoLogin.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 230, 201), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        campoLogin.setBackground(CINZA_CLARO);
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 15, 0);
        painelDireito.add(campoLogin, gbc);

        // Campo senha
        JLabel labelSenha = new JLabel("SENHA");
        labelSenha.setFont(new Font("Arial", Font.BOLD, 11));
        labelSenha.setForeground(VERDE_ESCURO);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 4, 0);
        painelDireito.add(labelSenha, gbc);

        campoSenha = new JPasswordField();
        campoSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        campoSenha.setPreferredSize(new Dimension(280, 38));
        campoSenha.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 230, 201), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        campoSenha.setBackground(CINZA_CLARO);
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 25, 0);
        painelDireito.add(campoSenha, gbc);

        // Botão
        botaoEntrar = new JButton("ENTRAR") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getModel().isPressed() ? VERDE_ESCURO :
                        getModel().isRollover() ? VERDE_MEDIO : VERDE_CLARO);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2d.setColor(BRANCO);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), x, y);
            }
        };
        botaoEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        botaoEntrar.setForeground(BRANCO);
        botaoEntrar.setFocusPainted(false);
        botaoEntrar.setBorderPainted(false);
        botaoEntrar.setContentAreaFilled(false);
        botaoEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botaoEntrar.setPreferredSize(new Dimension(280, 42));
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 0, 0);
        painelDireito.add(botaoEntrar, gbc);

        botaoEntrar.addActionListener(e -> autenticar());
        campoSenha.addActionListener(e -> autenticar());

        painelPrincipal.add(painelEsquerdo, BorderLayout.WEST);
        painelPrincipal.add(painelDireito, BorderLayout.CENTER);

        add(painelPrincipal);
        setVisible(true);
    }

    private void autenticar() {
        String login = campoLogin.getText();
        String senha = new String(campoSenha.getPassword());
        LoginController controller = new LoginController();
        controller.autenticar(login, senha, this);
    }

    public static void main(String[] args) {
        new TelaLogin();
    }
}