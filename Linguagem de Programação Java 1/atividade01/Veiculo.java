public class Veiculo {
	private String placa;
	private String marca;
	private String modelo;
	private String cor;
	private float velocMax;
	private int qtdRodas;
	private Motor motor;
	
	public Veiculo {
		this.placa = '';
		this.marca = '';
		this.modelo = '';
		this.cor = '';
		this.velocMax = 0;
		this.qtdRodas = 0;
		motor = new Motor();
	}
	
	public Veiculo(String placa, String marca, String modelo, String cor, float velocMax, int qtdRodas, Motor motor) {
			this.placa = placa;
			this.marca = marca;
			this.modelo = modelo;
			this.cor = cor;
			this.velocMax = velocMax
			this.qtdRodas = qtdRodas;
			this.motor = New Motor();
	}
	
	// Getters
	
	public String getPlaca {
		return this.placa;
	}
	
	public String getMarca {
		return this.marca;
	}
	
	public String getModelo {
		return this.modelo;
	}
	
	public String getCor {
		return this.cor;
	}
	
	public float getVelocMax() {
		return this.velocMax;
	}
	
	public int getQtdRodas() {
		return this.qtdRodas;
	}
	
	// Setters
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public void setMarca(String marca) {
		this.marca = marca;
	}
	
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	
	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public float setVelocMax(float velocMax) {
		this.velocMax = velocMax;
	}
	
	public int setQtdRodas(int qtdRodas) {
		this.qtdRodas = qtdRodas;
	}
	
	
}