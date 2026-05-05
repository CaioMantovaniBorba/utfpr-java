package atividade07.main;

import java.io.IOException;

import atividade07.entities.Carga;
import atividade07.entities.Passeio;
import atividade07.exception.VeicExistException;
import atividade07.exception.VelocException;
import atividade07.utils.Leitura;

// cd "c:\git\utfpr-java\Linguagem de Programação Java 1\atividade07" && mvn exec:java -Dexec.mainClass="atividade07.main.Teste"

public class Teste {
	private static final int TAMANHO_MAXIMO = 5;
	private static final float VELOCIDADE_PADRAO_PASSEIO = 100;
	private static final float VELOCIDADE_PADRAO_CARGA = 90;

	public static void main(String[] args) {
		Leitura leitura = new Leitura();
		Passeio[] veiculosPasseio = new Passeio[TAMANHO_MAXIMO];
		Carga[] veiculosCarga = new Carga[TAMANHO_MAXIMO];

		try {
			exibirMenuInicial(leitura, veiculosPasseio, veiculosCarga);
		} catch (IOException e) {
			System.out.println("Erro ao ler dados: " + e.getMessage());
		}
	}

	private static void exibirMenuInicial(Leitura leitura, Passeio[] veiculosPasseio, Carga[] veiculosCarga)
			throws IOException {
		int opcao;

		do {
			System.out.println("\nSistema de Gestao de Veiculos - Menu Inicial");
			System.out.println("1. Cadastrar Veiculo de Passeio");
			System.out.println("2. Cadastrar Veiculo de Carga");
			System.out.println("3. Imprimir Todos os Veiculos de Passeio");
			System.out.println("4. Imprimir Todos os Veiculos de Carga");
			System.out.println("5. Imprimir Veiculo de Passeio pela Placa");
			System.out.println("6. Imprimir Veiculo de Carga pela Placa");
			System.out.println("7. Sair do Sistema");

			opcao = lerInteiro(leitura, "Escolha uma opcao: ");
			executarOpcao(leitura, opcao, veiculosPasseio, veiculosCarga);
		} while (opcao != 7);
	}

	private static void executarOpcao(Leitura leitura, int opcao, Passeio[] veiculosPasseio, Carga[] veiculosCarga)
			throws IOException {
		switch (opcao) {
		case 1:
			cadastrarVeiculosPasseio(leitura, veiculosPasseio, veiculosCarga);
			break;
		case 2:
			cadastrarVeiculosCarga(leitura, veiculosPasseio, veiculosCarga);
			break;
		case 3:
			imprimirTodosPasseio(veiculosPasseio);
			break;
		case 4:
			imprimirTodosCarga(veiculosCarga);
			break;
		case 5:
			imprimirPasseioPorPlaca(leitura, veiculosPasseio);
			break;
		case 6:
			imprimirCargaPorPlaca(leitura, veiculosCarga);
			break;
		case 7:
			System.out.println("Sistema encerrado.");
			break;
		default:
			System.out.println("Opcao invalida. Escolha uma opcao de 1 a 7.");
			break;
		}
	}

	private static void cadastrarVeiculosPasseio(Leitura leitura, Passeio[] veiculosPasseio, Carga[] veiculosCarga)
			throws IOException {
		String resposta;

		do {
			if (estaCheio(veiculosPasseio)) {
				System.out.println("Nao ha espaco para cadastrar mais veiculos de passeio.");
				return;
			}

			try {
				Passeio passeio = new Passeio();
				preencherDadosComuns(leitura, passeio, veiculosPasseio, veiculosCarga);
				passeio.setVelocMax(validarVelocidade(leitura, VELOCIDADE_PADRAO_PASSEIO));
				passeio.setQtdPassageiros(lerInteiro(leitura, "Quantidade de passageiros: "));

				veiculosPasseio[proximaPosicaoLivre(veiculosPasseio)] = passeio;
				System.out.println("Veiculo de passeio cadastrado com sucesso.");
			} catch (VeicExistException | VelocException e) {
				System.out.println(e.getMessage());
				if (e instanceof VeicExistException) {
					return;
				}
			}

			resposta = leitura.entDados("Deseja cadastrar mais um veiculo de passeio? (SIM/NAO): ").trim();
		} while (resposta.equalsIgnoreCase("SIM"));
	}

	private static void cadastrarVeiculosCarga(Leitura leitura, Passeio[] veiculosPasseio, Carga[] veiculosCarga)
			throws IOException {
		String resposta;

		do {
			if (estaCheio(veiculosCarga)) {
				System.out.println("Nao ha espaco para cadastrar mais veiculos de carga.");
				return;
			}

			try {
				Carga carga = new Carga();
				preencherDadosComuns(leitura, carga, veiculosPasseio, veiculosCarga);
				carga.setVelocMax(validarVelocidade(leitura, VELOCIDADE_PADRAO_CARGA));
				carga.setCargaMax(lerInteiro(leitura, "Carga maxima: "));
				carga.setTara(lerInteiro(leitura, "Tara: "));

				veiculosCarga[proximaPosicaoLivre(veiculosCarga)] = carga;
				System.out.println("Veiculo de carga cadastrado com sucesso.");
			} catch (VeicExistException | VelocException e) {
				System.out.println(e.getMessage());
				if (e instanceof VeicExistException) {
					return;
				}
			}

			resposta = leitura.entDados("Deseja cadastrar mais um veiculo de carga? (SIM/NAO): ").trim();
		} while (resposta.equalsIgnoreCase("SIM"));
	}

	private static void preencherDadosComuns(Leitura leitura, atividade07.entities.Veiculo veiculo,
			Passeio[] veiculosPasseio, Carga[] veiculosCarga) throws IOException, VeicExistException {
		String placa = leitura.entDados("Placa: ").trim();
		if (buscarPasseio(veiculosPasseio, placa) != null || buscarCarga(veiculosCarga, placa) != null) {
			throw new VeicExistException();
		}

		veiculo.setPlaca(placa);
		veiculo.setMarca(leitura.entDados("Marca: "));
		veiculo.setModelo(leitura.entDados("Modelo: "));
		veiculo.setCor(leitura.entDados("Cor: "));
		veiculo.setQtdRodas(lerInteiro(leitura, "Quantidade de rodas: "));
		veiculo.getMotor().setQtdPist(lerInteiro(leitura, "Quantidade de pistoes do motor: "));
		veiculo.getMotor().setPotencia(lerInteiro(leitura, "Potencia do motor: "));
	}

	private static float validarVelocidade(Leitura leitura, float velocidadePadrao) throws IOException, VelocException {
		float velocidade = lerFloat(leitura, "Velocidade maxima em Km/h: ");

		if (velocidade < 80 || velocidade > 110) {
			try {
				throw new VelocException();
			} catch (VelocException e) {
				System.out.println(e.getMessage());
				return velocidadePadrao;
			}
		}

		return velocidade;
	}

	private static void imprimirTodosPasseio(Passeio[] veiculosPasseio) {
		System.out.println("\n========== VEICULOS DE PASSEIO ==========");
		boolean encontrou = false;

		for (Passeio passeio : veiculosPasseio) {
			if (passeio != null) {
				imprimirPasseio(passeio);
				encontrou = true;
			}
		}

		if (!encontrou) {
			System.out.println("Nenhum veiculo de passeio cadastrado.");
		}
	}

	private static void imprimirTodosCarga(Carga[] veiculosCarga) {
		System.out.println("\n========== VEICULOS DE CARGA ==========");
		boolean encontrou = false;

		for (Carga carga : veiculosCarga) {
			if (carga != null) {
				imprimirCarga(carga);
				encontrou = true;
			}
		}

		if (!encontrou) {
			System.out.println("Nenhum veiculo de carga cadastrado.");
		}
	}

	private static void imprimirPasseioPorPlaca(Leitura leitura, Passeio[] veiculosPasseio) throws IOException {
		String placa = leitura.entDados("Informe a placa do veiculo de passeio: ").trim();
		Passeio passeio = buscarPasseio(veiculosPasseio, placa);

		if (passeio == null) {
			System.out.println("Nao existe veiculo de passeio com esta placa.");
			return;
		}

		imprimirPasseio(passeio);
	}

	private static void imprimirCargaPorPlaca(Leitura leitura, Carga[] veiculosCarga) throws IOException {
		String placa = leitura.entDados("Informe a placa do veiculo de carga: ").trim();
		Carga carga = buscarCarga(veiculosCarga, placa);

		if (carga == null) {
			System.out.println("Nao existe veiculo de carga com esta placa.");
			return;
		}

		imprimirCarga(carga);
	}

	private static void imprimirPasseio(Passeio passeio) {
		System.out.println("\nPlaca: " + passeio.getPlaca());
		System.out.println("Marca: " + passeio.getMarca());
		System.out.println("Modelo: " + passeio.getModelo());
		System.out.println("Cor: " + passeio.getCor());
		System.out.println("Velocidade maxima em Km/h: " + passeio.getVelocMax());
		System.out.println("Quantidade de rodas: " + passeio.getQtdRodas());
		System.out.println("Quantidade de pistoes do motor: " + passeio.getMotor().getQtdPist());
		System.out.println("Potencia do motor: " + passeio.getMotor().getPotencia());
		System.out.println("Quantidade de passageiros: " + passeio.getQtdPassageiros());
		System.out.println("Resultado calcular: " + passeio.calcular());
		System.out.println("Velocidade convertida em m/h: " + passeio.calcVel(passeio.getVelocMax()));
		System.out.println("==========");
	}

	private static void imprimirCarga(Carga carga) {
		System.out.println("\nPlaca: " + carga.getPlaca());
		System.out.println("Marca: " + carga.getMarca());
		System.out.println("Modelo: " + carga.getModelo());
		System.out.println("Cor: " + carga.getCor());
		System.out.println("Velocidade maxima em Km/h: " + carga.getVelocMax());
		System.out.println("Quantidade de rodas: " + carga.getQtdRodas());
		System.out.println("Quantidade de pistoes do motor: " + carga.getMotor().getQtdPist());
		System.out.println("Potencia do motor: " + carga.getMotor().getPotencia());
		System.out.println("Carga maxima: " + carga.getCargaMax());
		System.out.println("Tara: " + carga.getTara());
		System.out.println("Resultado calcular: " + carga.calcular());
		System.out.println("Velocidade convertida em cm/h: " + carga.calcVel(carga.getVelocMax()));
		System.out.println("==========");
	}

	private static Passeio buscarPasseio(Passeio[] veiculosPasseio, String placa) {
		for (Passeio passeio : veiculosPasseio) {
			if (passeio != null && passeio.getPlaca().equalsIgnoreCase(placa)) {
				return passeio;
			}
		}

		return null;
	}

	private static Carga buscarCarga(Carga[] veiculosCarga, String placa) {
		for (Carga carga : veiculosCarga) {
			if (carga != null && carga.getPlaca().equalsIgnoreCase(placa)) {
				return carga;
			}
		}

		return null;
	}

	private static int proximaPosicaoLivre(Object[] veiculos) {
		for (int i = 0; i < veiculos.length; i++) {
			if (veiculos[i] == null) {
				return i;
			}
		}

		return -1;
	}

	private static boolean estaCheio(Object[] veiculos) {
		return proximaPosicaoLivre(veiculos) == -1;
	}

	private static int lerInteiro(Leitura leitura, String mensagem) throws IOException {
		while (true) {
			try {
				return Integer.parseInt(leitura.entDados(mensagem).trim());
			} catch (NumberFormatException e) {
				System.out.println("Digite um numero inteiro valido.");
			}
		}
	}

	private static float lerFloat(Leitura leitura, String mensagem) throws IOException {
		while (true) {
			try {
				return Float.parseFloat(leitura.entDados(mensagem).trim());
			} catch (NumberFormatException e) {
				System.out.println("Digite um numero valido.");
			}
		}
	}
}
