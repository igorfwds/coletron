package br.com.coletron.model;

import java.time.LocalDateTime; // Para o campo data_hora

public class Descarte {

    private int id_descarte;
    private LocalDateTime data_hora; // DATETIME do SQL mapeia bem para LocalDateTime [cite: 2]
    private int id_usuario; // Chave estrangeira para Usuario [cite: 2]
    private int id_residuo; // Chave estrangeira para Residuo [cite: 2]

    // Opcionalmente, você poderia ter objetos Usuario e Residuo aqui em vez de apenas os IDs,
    // mas isso adicionaria complexidade ao carregamento (precisaria de JOINs ou buscas separadas no DAO).
    // private Usuario usuario;
    // private Residuo residuo;

    // Construtor padrão
    public Descarte() {
    }

    // Construtor para criar um novo descarte (sem ID, pois será gerado pelo banco)
    // data_hora pode ser preenchida automaticamente pelo banco (DEFAULT CURRENT_TIMESTAMP)
    // ou definida pela aplicação.
    public Descarte(int id_usuario, int id_residuo, LocalDateTime data_hora) {
        this.id_usuario = id_usuario;
        this.id_residuo = id_residuo;
        this.data_hora = data_hora;
    }

    // Construtor para criar um novo descarte (sem ID, data_hora será o default do banco)
    public Descarte(int id_usuario, int id_residuo) {
        this.id_usuario = id_usuario;
        this.id_residuo = id_residuo;
        // this.data_hora será definido pelo banco ou pode ser preenchido antes de salvar
    }

    // Construtor para carregar um descarte do banco (com ID e data_hora)
    public Descarte(int id_descarte, int id_usuario, int id_residuo, LocalDateTime data_hora) {
        this.id_descarte = id_descarte;
        this.id_usuario = id_usuario;
        this.id_residuo = id_residuo;
        this.data_hora = data_hora;
    }

    // Getters e Setters

    public int getId_descarte() {
        return id_descarte;
    }

    public void setId_descarte(int id_descarte) {
        this.id_descarte = id_descarte;
    }

    public LocalDateTime getData_hora() {
        return data_hora;
    }

    public void setData_hora(LocalDateTime data_hora) {
        this.data_hora = data_hora;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_residuo() {
        return id_residuo;
    }

    public void setId_residuo(int id_residuo) {
        this.id_residuo = id_residuo;
    }

    @Override
    public String toString() {
        return "Descarte{" +
                "id_descarte=" + id_descarte +
                ", data_hora=" + data_hora +
                ", id_usuario=" + id_usuario +
                ", id_residuo=" + id_residuo +
                '}';
    }
}