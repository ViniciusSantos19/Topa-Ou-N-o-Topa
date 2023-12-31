package modelo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import modelo.Banco;
import modelo.Maleta;

public class TopaOuNãoTopa implements Runnable{
    private Socket client;
    private List<Maleta> listaMaletas;
    private int rodadaAtual;
    private int maletaDoJogador;
    private Banco banco;
    private int maxRodadas = 25;
    
    public TopaOuNãoTopa(Socket client) {
        this.client = client;
        this.listaMaletas = new ArrayList<Maleta>();
        this.rodadaAtual = 0;
        this.banco = new Banco();
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

    public String imprimirTabelaMaletas(){
        int numColunasPares = 7;
        int numColunasImpares = 6;
        StringBuffer str = new StringBuffer();
        for(int linha =0; linha < 4; linha++){
            System.out.println();
            for (int coluna = 0; coluna < (linha % 2 == 0 ? numColunasPares : numColunasImpares); coluna++) {
                int index = linha * (linha % 2 == 0 ? numColunasPares : numColunasImpares) + coluna;
                if (index < listaMaletas.size()){
                	str.append("| "+this.listaMaletas.get(index).getMascara()+" |, "+"\n");
                }

            }
        }
        return str.toString();
    }

    @Override
    public void run() {
    	try {
			Scanner in = new Scanner(this.client.getInputStream());
			PrintWriter out = new PrintWriter(this.client.getOutputStream(), true);
			while(this.rodadaAtual <= this.maxRodadas) {
				out.println("----- A rodada atual "+this.rodadaAtual+" ----- ");
				out.println(this.imprimirTabelaMaletas());
				out.println("Escolha uma maleta para abrir");
				int numMaleta = in.nextInt();
				abrirMaleta(numMaleta);
				out.println("A oferta do banco é:" +this.banco.calcularOferta(listaMaletas, numMaleta));
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
