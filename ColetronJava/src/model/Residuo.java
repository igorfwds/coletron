package model;

public class Residuo {
    private String tipo;
    private int pontos;

    public Residuo(String tipo, int pontos) {
        this.tipo = tipo;
        this.pontos = pontos;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getPontos() { return pontos; }
    public void setPontos(int pontos) { this.pontos = pontos; }
}