package br.com.coletron.model;

public class Usuario {

    private int id_usuario; // Novo campo para corresponder à PK da tabela [cite: 2]
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private int pontos_acum; // Corresponde a 'pontos_acum' da tabela [cite: 2]

    // Construtor padrão (útil para algumas bibliotecas e frameworks)
    public Usuario() {
    }

    // Construtor para criar um novo usuário (sem ID, pois será gerado pelo banco)
    // e com pontos iniciais (que pode ser 0)
    public Usuario(String nome, String cpf, String email, String senha, int pontos_acum) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.pontos_acum = pontos_acum;
    }

    // Construtor para carregar um usuário do banco (com ID)
    public Usuario(int id_usuario, String nome, String cpf, String email, String senha, int pontos_acum) {
        this.id_usuario = id_usuario;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.pontos_acum = pontos_acum;
    }

    // Getters e Setters para todos os campos

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getPontos_acum() {
        return pontos_acum;
    }

    public void setPontos_acum(int pontos_acum) {
        this.pontos_acum = pontos_acum;
    }

    // Método para adicionar pontos, se você mantiver essa lógica no model
    // Este método precisa ser compatível com o que UsuarioService espera.
    // O nome do campo no banco é pontos_acum, então o getter/setter deve refletir isso.
    // O método que você tinha em TelaPrincipal era usuarioAtual.getPontos() e
    // usuarioAtual.adicionarPontos(). Vamos manter um método similar para compatibilidade,
    // mas ele operará sobre pontos_acum.
    public int getPontos() { // Para manter compatibilidade com chamadas existentes
        return pontos_acum;
    }

    public void setPontos(int pontos) { // Para manter compatibilidade
        this.pontos_acum = pontos;
    }

    public void adicionarPontos(int pontosParaAdicionar) {
        this.pontos_acum += pontosParaAdicionar;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                // Não inclua a senha no toString por segurança
                ", pontos_acum=" + pontos_acum +
                '}';
    }
}