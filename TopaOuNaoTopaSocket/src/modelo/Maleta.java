package modelo;

public class Maleta {
    private Double valor;
    private boolean aberto;
    private String mascara;

    public Maleta(Double valor) {
        this.valor = valor;
        this.aberto =  false;
        this.mascara="*";
    }

    public Double getValor() {
        return valor;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void abrirMaleta(){
        this.aberto =  true;
        this.mascara = " "+this.valor;
    }

    public String getMascara() {
        return mascara;
    }
}
