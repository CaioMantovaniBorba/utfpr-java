package atividade09.gui;

import atividade09.entities.Carga;
import atividade09.entities.Passeio;
import atividade09.entities.Veiculo;

final class FormatadorVeiculo {
	private FormatadorVeiculo() {
	}

	static String formatar(Passeio passeio) {
		StringBuilder sb = new StringBuilder();
		adicionarDadosComuns(sb, passeio);
		sb.append("Quantidade de passageiros: ").append(passeio.getQtdPassageiros()).append('\n');
		sb.append("Resultado calcular: ").append(passeio.calcular()).append('\n');
		sb.append("Velocidade convertida em m/h: ").append(passeio.calcVel(passeio.getVelocMax()));
		return sb.toString();
	}

	static String formatar(Carga carga) {
		StringBuilder sb = new StringBuilder();
		adicionarDadosComuns(sb, carga);
		sb.append("Carga maxima: ").append(carga.getCargaMax()).append('\n');
		sb.append("Tara: ").append(carga.getTara()).append('\n');
		sb.append("Resultado calcular: ").append(carga.calcular()).append('\n');
		sb.append("Velocidade convertida em cm/h: ").append(carga.calcVel(carga.getVelocMax()));
		return sb.toString();
	}

	private static void adicionarDadosComuns(StringBuilder sb, Veiculo veiculo) {
		sb.append("Placa: ").append(veiculo.getPlaca()).append('\n');
		sb.append("Marca: ").append(veiculo.getMarca()).append('\n');
		sb.append("Modelo: ").append(veiculo.getModelo()).append('\n');
		sb.append("Cor: ").append(veiculo.getCor()).append('\n');
		sb.append("Velocidade maxima em Km/h: ").append(veiculo.getVelocMax()).append('\n');
		sb.append("Quantidade de rodas: ").append(veiculo.getQtdRodas()).append('\n');
		sb.append("Quantidade de pistoes do motor: ").append(veiculo.getMotor().getQtdPist()).append('\n');
		sb.append("Potencia do motor: ").append(veiculo.getMotor().getPotencia()).append('\n');
	}
}
