package br.com.coletron.model;

public class Residuo {

    private int id_residuo; // Novo campo para corresponder à PK da tabela [cite: 2]
    private String tipo;
    private int pontos_residuo; // Corresponde a 'pontos_residuo' da tabela [cite: 2]

    // Construtor padrão
    public Residuo() {
    }

    // Construtor para criar um novo resíduo (sem ID, pois será gerado pelo banco)
    public Residuo(String tipo, int pontos_residuo) {
        this.tipo = tipo;
        this.pontos_residuo = pontos_residuo;
    }

    // Construtor para carregar um resíduo do banco (com ID)
    public Residuo(int id_residuo, String tipo, int pontos_residuo) {
        this.id_residuo = id_residuo;
        this.tipo = tipo;
        this.pontos_residuo = pontos_residuo;
    }

    // Getters e Setters

    public int getId_residuo() {
        return id_residuo;
    }

    public void setId_residuo(int id_residuo) {
        this.id_residuo = id_residuo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Para manter compatibilidade com o código existente em TelaPrincipal
    // que usava new Residuo("Médio", 30) e depois residuo.getPontos()
    public int getPontos() {
        return pontos_residuo;
    }

    public int getPontos_residuo() {
        return pontos_residuo;
    }

    public void setPontos_residuo(int pontos_residuo) {
        this.pontos_residuo = pontos_residuo;
    }

    @Override
    public String toString() {
        return "Residuo{" +
                "id_residuo=" + id_residuo +
                ", tipo='" + tipo + '\'' +
                ", pontos_residuo=" + pontos_residuo +
                '}';
    }
}