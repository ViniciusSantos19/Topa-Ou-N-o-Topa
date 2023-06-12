package modelo;

import java.net.Socket;

public class MainTeste {
    public static void main(String[] args) {
        TopaOuNãoTopa topaOuNãoTopa = new TopaOuNãoTopa(new Socket());
        topaOuNãoTopa.abrirMaleta(1);
        topaOuNãoTopa.imprimirTabelaMaletas();
    }
}
