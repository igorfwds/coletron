package view;

import service.UsuarioService;
import model.Usuario;
import model.Residuo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import com.fazecast.jSerialComm.SerialPort;

public class TelaPrincipal extends JFrame {
    private UsuarioService usuarioService = new UsuarioService();
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private Usuario usuarioAtual;
    private int pontosIniciais;
    private SerialPort serialPort;

    public TelaPrincipal() {
        setTitle("COLETRON");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal com imagem de fundo
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("src/images/tela-principal.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelPrincipal.setLayout(null);

        // Criando e estilizando os botões
        JButton btnCadastrar = createRoundedButton("Cadastrar");
        JButton btnLogin = createRoundedButton("Já tenho login");
        JButton btnDescartar = createRoundedButton("Somente Descartar");

        // Adicionando ações aos botões
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Cadastro");
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Login");
            }
        });

        btnDescartar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Descartar");
            }
        });

        // Posicionando os botões na lateral direita
        int buttonWidth = 200;
        int buttonHeight = 50;
        int xPosition = getWidth() - buttonWidth - 100;
        btnCadastrar.setBounds(xPosition, 220, buttonWidth, buttonHeight);
        btnLogin.setBounds(xPosition, 280, buttonWidth, buttonHeight);
        btnDescartar.setBounds(xPosition, 340, buttonWidth, buttonHeight);

        panelPrincipal.add(btnCadastrar);
        panelPrincipal.add(btnLogin);
        panelPrincipal.add(btnDescartar);

        // Adicionando o painel principal ao CardLayout
        mainPanel.add(panelPrincipal, "Principal");
        mainPanel.add(createCadastroPanel(), "Cadastro");
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createDescartarPanel(), "Descartar");
        mainPanel.add(createSelecaoResiduoPanel(), "Selecao");
        mainPanel.add(createEsperaPanel(), "Espera");
        mainPanel.add(createPerguntaPanel(), "Pergunta");
        mainPanel.add(createAcessoPanel(), "Acesso");

        add(mainPanel);
        
        iniciarComunicacaoSerial();
    }

    private JPanel createCadastroPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("src/images/cadastro.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        JButton btnVoltar = createImageButton("src/images/voltar.jpg");
        btnVoltar.setBounds(40, 30, 50, 40);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Principal");
            }
        });
        panel.add(btnVoltar);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(240, 150, 290, 30);
        panel.add(lblNome);

        JTextField txtNome = new JTextField(20);
        txtNome.setBounds(240, 180, 290, 30);
        panel.add(txtNome);

        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(240, 210, 290, 30);
        panel.add(lblCpf);

        JTextField txtCpf = new JTextField(20);
        txtCpf.setBounds(240, 240, 290, 30);
        panel.add(txtCpf);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(240, 270, 290, 30);
        panel.add(lblEmail);

        JTextField txtEmail = new JTextField(20);
        txtEmail.setBounds(240, 300, 290, 30);
        panel.add(txtEmail);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(240, 330, 290, 30);
        panel.add(lblSenha);

        JPasswordField txtSenha = new JPasswordField(20);
        txtSenha.setBounds(240, 360, 290, 30);
        panel.add(txtSenha);

        JLabel lblConfirmaSenha = new JLabel("Confirmar Senha:");
        lblConfirmaSenha.setBounds(240, 390, 290, 30);
        panel.add(lblConfirmaSenha);

        JPasswordField txtConfirmaSenha = new JPasswordField(20);
        txtConfirmaSenha.setBounds(240, 420, 290, 30);
        panel.add(txtConfirmaSenha);

        JButton btnCadastrar = createRoundedButton("OK");
        btnCadastrar.setBounds(600, 300, 50, 40);
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String cpf = txtCpf.getText();
                String email = txtEmail.getText();
                String senha = new String(txtSenha.getPassword());
                String confirmaSenha = new String(txtConfirmaSenha.getPassword());

                if (!senha.equals(confirmaSenha)) {
                    JOptionPane.showMessageDialog(null, "As senhas não coincidem!");
                    return;
                }

                try {
                    usuarioService.cadastrarUsuario(nome, cpf, email, senha);
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                    cardLayout.show(mainPanel, "Principal");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar usuário: " + ex.getMessage());
                }
            }
        });
        panel.add(btnCadastrar);

        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("src/images/login.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        JButton btnVoltar = createImageButton("src/images/voltar.jpg");
        btnVoltar.setBounds(40, 30, 50, 40);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Principal");
            }
        });
        panel.add(btnVoltar);

        JLabel lblCpf = new JLabel("Digite seu CPF:");
        lblCpf.setBounds(280, 260, 200, 30);
        panel.add(lblCpf);

        JTextField txtCpf = new JTextField(20);
        txtCpf.setBounds(280, 300, 260, 30);
        txtCpf.setToolTipText("CPF");
        panel.add(txtCpf);

        JButton btnLogin = createRoundedButton("OK");
        btnLogin.setBounds(600, 295, 50, 40);
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cpf = txtCpf.getText();

                try {
                    usuarioAtual = usuarioService.loginUsuario(cpf);
                    if (usuarioAtual != null) {
                        pontosIniciais = usuarioAtual.getPontos();
                        txtCpf.setText("");
                        cardLayout.show(mainPanel, "Selecao");
                    } else {
                        JOptionPane.showMessageDialog(null, "CPF não encontrado. Por favor, cadastre-se.");
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao buscar usuário: " + ex.getMessage());
                }
            }
        });
        panel.add(btnLogin);

        return panel;
    }

    private JPanel createDescartarPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("src/images/sem-pontuar.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);
        
        JButton btnVoltar = createImageButton("src/images/voltar.jpg");
        btnVoltar.setBounds(40, 30, 50, 40);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Principal");
            }
        });
        panel.add(btnVoltar);

        JButton btnCadastrar = createRoundedButton("Cadastrar");
        btnCadastrar.setBounds(400, 300, 200, 50);
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Cadastro");
            }
        });
        panel.add(btnCadastrar);

        JButton btnNao = createRoundedButton("Tudo bem!");
        btnNao.setBounds(400, 370, 200, 50);
        btnNao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Selecao");
            }
        });
        panel.add(btnNao);

        return panel;
    }
    
    private JPanel createSelecaoResiduoPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("src/images/selecao-descarte.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        JButton btnVoltar = createImageButton("src/images/voltar.jpg");
        btnVoltar.setBounds(40, 30, 50, 40);
        btnVoltar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Principal");
            }
        });
        panel.add(btnVoltar);

        JButton btnPequeno = createRoundedButton("Pequeno");
        btnPequeno.setBounds(100, 250, 200, 50);
        btnPequeno.setBackground(Color.decode("#91a7e0")); // Cor de fundo
        btnPequeno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processarDescarte(new Residuo("Pequeno", 10));
                enviarComandoParaArduino("A"); // Envia comando para abrir a porta
                cardLayout.show(mainPanel, "Espera");
            }
        });
        panel.add(btnPequeno);

        JButton btnMedio = createRoundedButton("Médio");
        btnMedio.setBounds(310, 250, 200, 50);
        btnMedio.setBackground(Color.decode("#91a7e0")); // Cor de fundo
        btnMedio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processarDescarte(new Residuo("Médio", 30));
                enviarComandoParaArduino("A"); // Envia comando para abrir a porta
                cardLayout.show(mainPanel, "Espera");
            }
        });
        panel.add(btnMedio);

        JButton btnGrande = createRoundedButton("Grande");
        btnGrande.setBounds(520, 250, 200, 50);
        btnGrande.setBackground(Color.decode("#91a7e0")); // Cor de fundo
        btnGrande.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processarDescarte(new Residuo("Grande", 50));
                enviarComandoParaArduino("A"); // Envia comando para abrir a porta
                cardLayout.show(mainPanel, "Espera");
            }
        });
        panel.add(btnGrande);

        return panel;
    }
    
    private JPanel createEsperaPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("src/images/tela-espera.png");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        JButton btnProximo = createRoundedButton("Próximo");
        btnProximo.setBounds(310, 430, 200, 50);
        btnProximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarComandoParaArduino("B"); // Envia comando para fechar a porta
                cardLayout.show(mainPanel, "Pergunta"); // Substitua "Pergunta" pelo nome da tela que você deseja mostrar
                mainPanel.add(createPontosRecebidosPanel(), "Pontos");
            }
        });
        panel.add(btnProximo);

        return panel;
    }
    
    private JPanel createPerguntaPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("src/images/tela-pergunta.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        JButton btnSim = createRoundedButton("Sim");
        btnSim.setBounds(190, 430, 200, 50);
        btnSim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Selecao"); // Substitua "Selecao" pelo nome da tela que você deseja mostrar
            }
        });
        panel.add(btnSim);
        
        JButton btnNao = createRoundedButton("Não");
        btnNao.setBounds(450, 430, 200, 50);
        btnNao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Pontos"); // Substitua "Principal" pelo nome da tela que você deseja mostrar
            }
        });
        panel.add(btnNao);

        return panel;
    }
    
    private JPanel createPontosRecebidosPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("src/images/pontos-recebidos.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);
        
        int pontosGanhos = usuarioAtual.getPontos() - pontosIniciais;
        
        // Criando JLabel para pontosGanhos com fonte grande
        JLabel lblPontosGrande = new JLabel("+" + String.valueOf(pontosGanhos));
        lblPontosGrande.setFont(new Font("Montserrat", Font.BOLD, 90)); // Define a fonte Arial, negrito, tamanho 30
        lblPontosGrande.setForeground(Color.decode("#FFFFFF"));
        lblPontosGrande.setBounds(100, 155, 500, 100);
        panel.add(lblPontosGrande);

        // Criando JLabel para pontosGanhos com fonte pequena
        JLabel lblPontosPequeno = new JLabel(String.valueOf(pontosGanhos));
        lblPontosPequeno.setFont(new Font("Montserrat", Font.BOLD, 35)); // Define a fonte Arial, normal, tamanho 15
        lblPontosPequeno.setForeground(Color.decode("#015B74"));
        lblPontosPequeno.setBounds(655, 170, 200, 45);
        panel.add(lblPontosPequeno);

        JButton btnProximo = createRoundedButton("Próximo");
        btnProximo.setBounds(450, 360, 180, 50);
        btnProximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Acesso"); // Substitua "Selecao" pelo nome da tela que você deseja mostrar
            }
        });
        panel.add(btnProximo);

        return panel;
    }
    
    private JPanel createAcessoPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("src/images/acesso.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        JButton btnProximo = createRoundedButton("Finalizar");
        btnProximo.setBounds(450, 380, 180, 50);
        btnProximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Principal"); // Substitua "Selecao" pelo nome da tela que você deseja mostrar
            }
        });
        panel.add(btnProximo);

        return panel;
    }

    private void processarDescarte(Residuo residuo) {
        try {
            usuarioService.adicionarPontos(usuarioAtual, residuo);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao processar o descarte: " + ex.getMessage());
        }
    }

    
    private JButton createRoundedButton(String text) {
        RoundedButton button = new RoundedButton(text, 20);
        button.setBackground(new Color(95, 193, 152)); // Cor de fundo
        button.setForeground(new Color(3, 59, 75)); // Cor do texto
        button.setFont(new Font("Arial", Font.BOLD, 16));
        return button;
    }

    private JButton createImageButton(String imagePath) {
        JButton button = new JButton(new ImageIcon(imagePath));
        button.setContentAreaFilled(false); // Torna o botão transparente
        button.setBorderPainted(false); // Remove a borda
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }
    
    private void iniciarComunicacaoSerial() {
        serialPort = SerialPort.getCommPort("COM4"); // Altere para a porta correta do seu Arduino
        serialPort.setBaudRate(9600);

        if (serialPort.openPort()) {
            System.out.println("Porta aberta com sucesso.");
        } else {
            System.out.println("Falha ao abrir a porta.");
        }
    }
    
    private void enviarComandoParaArduino(String comando) {
        if (serialPort != null && serialPort.isOpen()) {
            byte[] buffer = comando.getBytes();
            serialPort.writeBytes(buffer, buffer.length);
        } else {
            System.out.println("Porta serial não está aberta.");
        }
    }
}