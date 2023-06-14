package modelo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import modelo.Banco;
import modelo.Maleta;

public class TopaOuNãoTopa implements Runnable{
    private Socket client;
    private List<Maleta> listaMaletas;
    private int rodadaAtual;
    private Banco banco;
    private int maxRodadas = 25;
    private double ofertaDoBanco;
    
    public void setOfertaDoBanco() {
    	this.ofertaDoBanco = banco.calcularOferta(listaMaletas, rodadaAtual);
    }
    
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

    public String abrirMaleta(int posicao){
        if (posicao >= 0 && posicao < listaMaletas.size()){
            if(!this.listaMaletas.get(posicao).isAberto()) {
            	this.listaMaletas.get(posicao).abrirMaleta();
                this.rodadaAtual++;
                return "Maleta da Posicao: "+posicao+" aberta"; 
            }
        }
        return "Maleta já aberta ou posicao inválida";
    }
    
    private double acharUltimaMaleta() {
    	 Optional<Maleta> maletaFechada = listaMaletas.stream()
    		        .filter(maleta -> !maleta.isAberto())
    		        .findFirst();

    	 if (maletaFechada.isPresent()) {
    		     return maletaFechada.get().getValor();
    	 }
    		   return 0;
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
            str.append("\n");
            for (int coluna = 0; coluna < (linha % 2 == 0 ? numColunasPares : numColunasImpares); coluna++) {
                int index = linha * (linha % 2 == 0 ? numColunasPares : numColunasImpares) + coluna;
                if (index < listaMaletas.size()){
                	str.append("| "+this.listaMaletas.get(index).getMascara()+" |, ");
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
				out.println(abrirMaleta(numMaleta));
					
				
				setOfertaDoBanco();
				out.println("A oferta do banco é:" +this.ofertaDoBanco);
				out.println("Você topa ou não topa? (1 para sim 2 para não)");
				int simOuNao = in.nextInt();
				
				if(simOuNao == 1) {
					out.println("Você recebeu "+this.ofertaDoBanco+" reais");
					break;
				}
				
				if(rodadaAtual == 25) {
					out.println("Você recebeu "+this.acharUltimaMaleta());
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
