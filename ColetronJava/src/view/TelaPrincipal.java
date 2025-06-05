package view;

import service.UsuarioService;
import model.Usuario;
import model.Residuo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaPrincipal extends JFrame {
    private UsuarioService usuarioService = new UsuarioService();
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private Usuario usuarioAtual;
    private int pontosIniciais;

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
                ImageIcon icon = null;
                try {
                    java.net.URL imgUrl = getClass().getResource("/images/tela-principal.jpg"); // Nota a barra inicial
                    if (imgUrl != null) {
                        icon = new ImageIcon(imgUrl);
                    } else {
                        System.err.println("Não foi possível encontrar a imagem: /images/tela-principal.jpg");
                        // Opcionalmente, pinte um fundo de cor sólida para indicar o erro
                        g.setColor(Color.RED);
                        g.fillRect(0, 0, getWidth(), getHeight());
                        return; // Não tenta desenhar a imagem se ela não foi carregada
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    // Opcionalmente, pinte um fundo de cor sólida para indicar o erro
                    g.setColor(Color.ORANGE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    return; // Não tenta desenhar a imagem se ela não foi carregada
                }

                if (icon != null && icon.getImage() != null) { // Verifique se o ícone e a imagem foram carregados
                    g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
                } else if (icon != null) { // Se o ícone foi criado mas a imagem não (caso raro com ImageIcon(URL))
                    System.err.println("ImageIcon foi criado, mas a imagem interna é nula para: /images/tela-principal.jpg");
                    g.setColor(Color.BLUE); // Cor para indicar este estado específico
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelPrincipal.setLayout(null);

        // Criando e estilizando os botões
        JButton btnCadastrar = createRoundedButton("Cadastrar");
        JButton btnLogin = createRoundedButton("Já tenho login");
        JLabel lblDescartar = createLinkLabel("Somente Descartar");

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

        lblDescartar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainPanel, "Selecao");
            }
        });

        // Posicionando os botões na lateral direita
        int buttonWidth = 200;
        int buttonHeight = 50;
        int xPosition = getWidth() - buttonWidth - 100;
        btnCadastrar.setBounds(xPosition, 220, buttonWidth, buttonHeight);
        btnLogin.setBounds(xPosition, 280, buttonWidth, buttonHeight);
        lblDescartar.setBounds(xPosition + 40, 340, buttonWidth, buttonHeight);

        panelPrincipal.add(btnCadastrar);
        panelPrincipal.add(btnLogin);
        panelPrincipal.add(lblDescartar);

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
                ImageIcon icon = null;
                try {
                    java.net.URL imgUrl = getClass().getResource("/images/cadastro.jpg"); // Caminho a partir da raiz do classpath
                    if (imgUrl != null) {
                        icon = new ImageIcon(imgUrl);
                    } else {
                        System.err.println("Não foi possível encontrar a imagem: /images/cadastro.jpg");
                        // Opcional: você pode querer lançar uma exceção aqui ou definir um ícone padrão
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar a imagem: /images/cadastro.jpg");
                    e.printStackTrace();
                    // Opcional: tratar a exceção ou definir um ícone padrão
                }
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


        JTextField txtNome = new JTextField(20);
        txtNome.setBounds(50, 230, 290, 30);
        panel.add(txtNome);

        JTextField txtCpf = new JTextField(20);
        txtCpf.setBounds(50, 300, 290, 30);
        panel.add(txtCpf);

        JTextField txtEmail = new JTextField(20);
        txtEmail.setBounds(50, 370, 290, 30);
        panel.add(txtEmail);

        JPasswordField txtSenha = new JPasswordField(20);
        txtSenha.setBounds(50, 440, 290, 30);
        panel.add(txtSenha);

        JPasswordField txtConfirmaSenha = new JPasswordField(20);
        txtConfirmaSenha.setBounds(50, 510, 290, 30);
        panel.add(txtConfirmaSenha);

        JButton btnCadastrar = createRoundedButton("Cadastrar");
        btnCadastrar.setBounds(550, 440, 200, 50);
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
                ImageIcon icon = null;
                try {
                    java.net.URL imgUrl = getClass().getResource("/images/login.jpg"); // Caminho a partir da raiz do classpath
                    if (imgUrl != null) {
                        icon = new ImageIcon(imgUrl);
                    } else {
                        System.err.println("Não foi possível encontrar a imagem: /images/login.jpg");
                        // Opcional: Lançar exceção ou definir um ícone padrão.
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar a imagem: /images/login.jpg");
                    e.printStackTrace();
                    // Opcional: Tratar a exceção ou definir um ícone padrão.
                }
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

        JTextField txtCpf = new JTextField(20);
        txtCpf.setBounds(150, 250, 500, 30);
        txtCpf.setToolTipText("CPF");
        panel.add(txtCpf);

        JButton btnLogin = createRoundedButton("Confirmar");
        btnLogin.setBounds(300, 350, 200, 50);
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
                ImageIcon icon = null;
                try {
                    java.net.URL imgUrl = getClass().getResource("/images/sem-pontuar.jpg"); // Caminho a partir da raiz do classpath
                    if (imgUrl != null) {
                        icon = new ImageIcon(imgUrl);
                    } else {
                        System.err.println("Não foi possível encontrar a imagem: /images/sem-pontuar.jpg");
                        // Opcional: Lançar exceção ou definir um ícone padrão.
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar a imagem: /images/sem-pontuar.jpg");
                    e.printStackTrace();
                    // Opcional: Tratar a exceção ou definir um ícone padrão.
                }
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
                ImageIcon icon = null;
                try {
                    java.net.URL imgUrl = getClass().getResource("/images/selecao-descarte.jpg"); // Caminho a partir da raiz do classpath
                    if (imgUrl != null) {
                        icon = new ImageIcon(imgUrl);
                    } else {
                        System.err.println("Não foi possível encontrar a imagem: /images/selecao-descarte.jpg");
                        // Opcional: Lançar exceção ou definir um ícone padrão.
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar a imagem: /images/selecao-descarte.jpg");
                    e.printStackTrace();
                    // Opcional: Tratar a exceção ou definir um ícone padrão.
                }
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
        btnPequeno.setBackground(Color.decode("#41ABC9")); // Cor de fundo
        btnPequeno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processarDescarte(new Residuo("Pequeno", 10));
                cardLayout.show(mainPanel, "Espera");
            }
        });
        panel.add(btnPequeno);

        JButton btnMedio = createRoundedButton("Médio");
        btnMedio.setBounds(310, 250, 200, 50);
        btnMedio.setBackground(Color.decode("#41ABC9")); // Cor de fundo
        btnMedio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processarDescarte(new Residuo("Médio", 30));
                cardLayout.show(mainPanel, "Espera");
            }
        });
        panel.add(btnMedio);

        JButton btnGrande = createRoundedButton("Grande");
        btnGrande.setBounds(520, 250, 200, 50);
        btnGrande.setBackground(Color.decode("#41ABC9")); // Cor de fundo
        btnGrande.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processarDescarte(new Residuo("Grande", 50));
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
                ImageIcon icon = null;
                try {
                    java.net.URL imgUrl = getClass().getResource("/images/tela-espera.jpg"); // Caminho a partir da raiz do classpath
                    if (imgUrl != null) {
                        icon = new ImageIcon(imgUrl);
                    } else {
                        System.err.println("Não foi possível encontrar a imagem: /images/tela-espera.jpg");
                        // Opcional: Lançar exceção ou definir um ícone padrão.
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar a imagem: /images/tela-espera.jpg");
                    e.printStackTrace();
                    // Opcional: Tratar a exceção ou definir um ícone padrão.
                }
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        JButton btnProximo = createRoundedButton("Próximo");
        btnProximo.setBounds(310, 430, 200, 50);
        btnProximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.add(createPontosRecebidosPanel(), "Pontos");
                cardLayout.show(mainPanel, "Pontos");
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
                ImageIcon icon = null;
                try {
                    java.net.URL imgUrl = getClass().getResource("/images/tela-pergunta.jpg"); // Caminho a partir da raiz do classpath
                    if (imgUrl != null) {
                        icon = new ImageIcon(imgUrl);
                    } else {
                        System.err.println("Não foi possível encontrar a imagem: /images/tela-pergunta.jpg");
                        // Opcional: Lançar exceção ou definir um ícone padrão.
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar a imagem: /images/tela-pergunta.jpg");
                    e.printStackTrace();
                    // Opcional: Tratar a exceção ou definir um ícone padrão.
                }
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
                ImageIcon icon = null;
                try {
                    java.net.URL imgUrl = getClass().getResource("/images/pontos-recebidos.jpg"); // Caminho a partir da raiz do classpath
                    if (imgUrl != null) {
                        icon = new ImageIcon(imgUrl);
                    } else {
                        System.err.println("Não foi possível encontrar a imagem: /images/pontos-recebidos.jpg");
                        // Opcional: Lançar exceção ou definir um ícone padrão.
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar a imagem: /images/pontos-recebidos.jpg");
                    e.printStackTrace();
                    // Opcional: Tratar a exceção ou definir um ícone padrão.
                }
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);
        
        int pontosGanhos = usuarioAtual.getPontos() - pontosIniciais;
    

        // Criando JLabel para pontosGanhos com fonte pequena
        JLabel lblPontosPequeno = new JLabel("+" + String.valueOf(pontosGanhos) + " PONTOS!");
        lblPontosPequeno.setFont(new Font("Montserrat", Font.BOLD, 50)); // Define a fonte Arial, normal, tamanho 15
        lblPontosPequeno.setForeground(Color.decode("#F2AD3C"));
        lblPontosPequeno.setBounds(400, 110, 600, 45);
        panel.add(lblPontosPequeno);

        JButton btnProximo = createRoundedButton("Finalizar");
        btnProximo.setBounds(300, 450, 180, 50);
        btnProximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Principal"); // Substitua "Selecao" pelo nome da tela que você deseja mostrar
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
                ImageIcon icon = null;
                try {
                    java.net.URL imgUrl = getClass().getResource("/images/acesso.jpg"); // Caminho a partir da raiz do classpath
                    if (imgUrl != null) {
                        icon = new ImageIcon(imgUrl);
                    } else {
                        System.err.println("Não foi possível encontrar a imagem: /images/acesso.jpg");
                        // Opcional: Lançar exceção ou definir um ícone padrão.
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao carregar a imagem: /images/acesso.jpg");
                    e.printStackTrace();
                    // Opcional: Tratar a exceção ou definir um ícone padrão.
                }
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
        button.setBackground(Color.decode("#41ABC9")); // Cor do botão
        button.setForeground(Color.decode("#FFFFFF")); // Cor da fonte
        button.setBorder(BorderFactory.createLineBorder(Color.decode("#0189AF"))); // Cor da borda
    
        try {
            Font montserrat = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-SemiBold.ttf"))
                                  .deriveFont(Font.BOLD, 16f);
            button.setFont(montserrat);
        } catch (Exception e) {
            button.setFont(new Font("Arial", Font.BOLD, 16)); // fallback
            e.printStackTrace();
        }
    
        return button;
    }    

    private JButton createImageButton(String imagePath) {
        JButton button = new JButton(new ImageIcon(imagePath));
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createLineBorder(Color.decode("#0189AF"))); // cor da borda, se desejar
        button.setForeground(Color.decode("#FFFFFF")); // cor da fonte, se tiver texto
        return button;
    }

    private JLabel createLinkLabel(String text) {
        JLabel label = new JLabel("<html><u>" + text + "</u></html>");
        label.setForeground(Color.decode("#033B4B")); // Cor da fonte
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
        try {
            Font montserrat = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Montserrat-SemiBold.ttf"))
                                  .deriveFont(Font.PLAIN, 14f);
            label.setFont(montserrat);
        } catch (Exception e) {
            label.setFont(new Font("Arial", Font.PLAIN, 14)); // fallback
            e.printStackTrace();
        }
    
        // Comportamento de clique
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Ação ao clicar no texto
                System.out.println("Texto clicado: Somente Descartar");
            }
    
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.decode("#0189AF")); // opcional: muda cor ao passar o mouse
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.decode("#033B4B"));
            }
        });
    
        return label;
    }    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }
    private boolean isSimulando = false;

    private void iniciarComunicacaoSerial() {
        // Simulação de conexão sem Arduino
        System.out.println("Simulando abertura da porta serial (sem Arduino).");
        // Pode usar um booleano para marcar que é simulação
        isSimulando = true;
    }
}