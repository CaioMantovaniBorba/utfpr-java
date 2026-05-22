package atividade09.gui;

enum TipoVeiculo {
	PASSEIO("Passeio", 100),
	CARGA("Carga", 90);

	private final String descricao;
	private final float velocidadePadrao;

	TipoVeiculo(String descricao, float velocidadePadrao) {
		this.descricao = descricao;
		this.velocidadePadrao = velocidadePadrao;
	}

	public String getDescricao() {
		return descricao;
	}

	public float getVelocidadePadrao() {
		return velocidadePadrao;
	}
}
