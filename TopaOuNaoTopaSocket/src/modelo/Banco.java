package modelo;

import java.util.List;

public class Banco {
	public Double calcularOferta(List<Maleta> maletas, int rodadaAtual) {
		
		Double valorTotalMaletas = maletas.stream().filter(maleta -> !maleta.isAberto())
	            .mapToDouble(Maleta::getValor)
	            .sum();
		int restanteMeletas = (int) maletas.stream().filter(a -> !a.isAberto()).count();
		
		double mediaValores = valorTotalMaletas / restanteMeletas;
	    double oferta = mediaValores * (1 - (rodadaAtual / 10.0));
		
		return oferta;
	}
}
