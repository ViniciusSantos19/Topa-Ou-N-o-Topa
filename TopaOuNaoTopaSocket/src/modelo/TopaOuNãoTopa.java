package modelo;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TopaOuNãoTopa implements Runnable{
    private Socket client;
    private List<Maleta> listaMaletas;
    private int rodadaAtual;
    private int maletaDoJogador;
    private double oferteaDoBanco;
    
    public TopaOuNãoTopa(Socket client) {
        this.client = client;
        this.listaMaletas = new ArrayList<Maleta>();
        this.rodadaAtual = 0;
        iniciarMaletas();
        sortear();
    }

    private void iniciarMaletas(){
        for (int i =0; i < 19; i++){
            listaMaletas.add(new Maleta(Math.pow(2,i)));
        }
        listaMaletas.add(new Maleta(300000.0));
        listaMaletas.add(new Maleta(350000.0));
        listaMaletas.add(new Maleta(375000.0));
        listaMaletas.add(new Maleta(400000.0));
        listaMaletas.add(new Maleta(450000.0));
        listaMaletas.add(new Maleta(500000.0));
        listaMaletas.add(new Maleta(1000000.0));
    }

    private void sortear(){
        Collections.shuffle(this.listaMaletas);
    }

    public void abrirMaleta(int posicao){
        if (posicao >= 0 && posicao < listaMaletas.size()){
            this.listaMaletas.get(posicao).abrirMaleta();
            this.rodadaAtual++;
        }
    }

    private void reiniciar(){
        this.sortear();
        this.rodadaAtual = 0;
    }

    public void imprimirTabelaMaletas(){
        int numColunasPares = 7;
        int numColunasImpares = 6;
        for(int linha =0; linha < 4; linha++){
            System.out.println();
            for (int coluna = 0; coluna < (linha % 2 == 0 ? numColunasPares : numColunasImpares); coluna++) {
                int index = linha * (linha % 2 == 0 ? numColunasPares : numColunasImpares) + coluna;
                if (index < listaMaletas.size()){
                    //this.listaMaletas.get(index).abrirMaleta();
                    System.out.print("| "+this.listaMaletas.get(index).getMascara()+" |, ");
                }

            }
        }
    }

    @Override
    public void run() {

    }
}
