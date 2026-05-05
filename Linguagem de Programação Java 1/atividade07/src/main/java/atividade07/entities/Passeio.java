package atividade07.entities;

public final class Passeio extends Veiculo implements Calcular {
	private int qtdPassageiros;

	public Passeio() {
		this.qtdPassageiros = 0;
	}

	public int getQtdPassageiros() {
		return qtdPassageiros;
	}

	public void setQtdPassageiros(int qtdPassageiros) {
		this.qtdPassageiros = qtdPassageiros;
	}

	@Override
	public final float calcVel(float velocMax) {
		return velocMax * 1000;
	}

	@Override
	public final int calcular() {
		return getPlaca().length() + getMarca().length() + getModelo().length() + getCor().length();
	}
}
