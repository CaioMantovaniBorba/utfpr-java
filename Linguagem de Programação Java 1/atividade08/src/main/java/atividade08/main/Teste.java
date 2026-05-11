package atividade08.main;

import java.io.IOException;

import atividade08.entities.BDVeiculo;
import atividade08.entities.Carga;
import atividade08.entities.Passeio;
import atividade08.exception.VeicExistException;
import atividade08.exception.VelocException;
import atividade08.utils.Leitura;

public class Teste {
	private static final float VELOCIDADE_PADRAO_PASSEIO = 100;
	private static final float VELOCIDADE_PADRAO_CARGA = 90;

	public static void main(String[] args) {
		Leitura leitura = new Leitura();
		BDVeiculo bdVeiculo = new BDVeiculo();

		try {
			exibirMenuInicial(leitura, bdVeiculo);
		} catch (IOException e) {
			System.out.println("Erro ao ler dados: " + e.getMessage());
		}
	}

	private static void exibirMenuInicial(Leitura leitura, BDVeiculo bdVeiculo) throws IOException {
		int opcao;

		do {
			System.out.println("\nSistema de Gestao de Veiculos - Menu Inicial");
			System.out.println("1. Cadastrar Veiculo de Passeio");
			System.out.println("2. Cadastrar Veiculo de Carga");
			System.out.println("3. Imprimir Todos os Veiculos de Passeio");
			System.out.println("4. Imprimir Todos os Veiculos de Carga");
			System.out.println("5. Imprimir Veiculo de Passeio pela Placa");
			System.out.println("6. Imprimir Veiculo de Carga pela Placa");
			System.out.println("7. Excluir Veiculo de Passeio pela Placa");
			System.out.println("8. Excluir Veiculo de Carga pela Placa");
			System.out.println("9. Sair do Sistema");

			opcao = lerInteiro(leitura, "Escolha uma opcao: ");
			executarOpcao(leitura, opcao, bdVeiculo);
		} while (opcao != 9);
	}

	private static void executarOpcao(Leitura leitura, int opcao, BDVeiculo bdVeiculo) throws IOException {
		switch (opcao) {
		case 1:
			cadastrarVeiculosPasseio(leitura, bdVeiculo);
			break;
		case 2:
			cadastrarVeiculosCarga(leitura, bdVeiculo);
			break;
		case 3:
			imprimirTodosPasseio(bdVeiculo);
			break;
		case 4:
			imprimirTodosCarga(bdVeiculo);
			break;
		case 5:
			imprimirPasseioPorPlaca(leitura, bdVeiculo);
			break;
		case 6:
			imprimirCargaPorPlaca(leitura, bdVeiculo);
			break;
		case 7:
			excluirPasseioPorPlaca(leitura, bdVeiculo);
			break;
		case 8:
			excluirCargaPorPlaca(leitura, bdVeiculo);
			break;
		case 9:
			System.out.println("Sistema encerrado.");
			break;
		default:
			System.out.println("Opcao invalida. Escolha uma opcao de 1 a 9.");
			break;
		}
	}

	private static void cadastrarVeiculosPasseio(Leitura leitura, BDVeiculo bdVeiculo) throws IOException {
		String resposta;

		do {
			try {
				Passeio passeio = new Passeio();
				preencherDadosComuns(leitura, passeio, bdVeiculo);
				passeio.setVelocMax(validarVelocidade(leitura, VELOCIDADE_PADRAO_PASSEIO));
				passeio.setQtdPassageiros(lerInteiro(leitura, "Quantidade de passageiros: "));

				bdVeiculo.adicionarPasseio(passeio);
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

	private static void cadastrarVeiculosCarga(Leitura leitura, BDVeiculo bdVeiculo) throws IOException {
		String resposta;

		do {
			try {
				Carga carga = new Carga();
				preencherDadosComuns(leitura, carga, bdVeiculo);
				carga.setVelocMax(validarVelocidade(leitura, VELOCIDADE_PADRAO_CARGA));
				carga.setCargaMax(lerInteiro(leitura, "Carga maxima: "));
				carga.setTara(lerInteiro(leitura, "Tara: "));

				bdVeiculo.adicionarCarga(carga);
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

	private static void preencherDadosComuns(Leitura leitura, atividade08.entities.Veiculo veiculo,
			BDVeiculo bdVeiculo) throws IOException, VeicExistException {
		String placa = leitura.entDados("Placa: ").trim();
		if (bdVeiculo.buscarPasseio(placa) != null || bdVeiculo.buscarCarga(placa) != null) {
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

	private static void imprimirTodosPasseio(BDVeiculo bdVeiculo) {
		System.out.println("\n========== VEICULOS DE PASSEIO ==========");

		if (bdVeiculo.getBDPas().isEmpty()) {
			System.out.println("Nenhum veiculo de passeio cadastrado.");
			return;
		}

		for (Passeio passeio : bdVeiculo.getBDPas()) {
			imprimirPasseio(passeio);
		}
	}

	private static void imprimirTodosCarga(BDVeiculo bdVeiculo) {
		System.out.println("\n========== VEICULOS DE CARGA ==========");

		if (bdVeiculo.getBDCarg().isEmpty()) {
			System.out.println("Nenhum veiculo de carga cadastrado.");
			return;
		}

		for (Carga carga : bdVeiculo.getBDCarg()) {
			imprimirCarga(carga);
		}
	}

	private static void imprimirPasseioPorPlaca(Leitura leitura, BDVeiculo bdVeiculo) throws IOException {
		String placa = leitura.entDados("Informe a placa do veiculo de passeio: ").trim();
		Passeio passeio = bdVeiculo.buscarPasseio(placa);

		if (passeio == null) {
			System.out.println("Nao existe veiculo de passeio com esta placa.");
			return;
		}

		imprimirPasseio(passeio);
	}

	private static void imprimirCargaPorPlaca(Leitura leitura, BDVeiculo bdVeiculo) throws IOException {
		String placa = leitura.entDados("Informe a placa do veiculo de carga: ").trim();
		Carga carga = bdVeiculo.buscarCarga(placa);

		if (carga == null) {
			System.out.println("Nao existe veiculo de carga com esta placa.");
			return;
		}

		imprimirCarga(carga);
	}

	private static void excluirPasseioPorPlaca(Leitura leitura, BDVeiculo bdVeiculo) throws IOException {
		String placa = leitura.entDados("Informe a placa do veiculo de passeio para excluir: ").trim();

		if (bdVeiculo.excluirPasseio(placa)) {
			System.out.println("Veiculo de passeio excluido com sucesso.");
			return;
		}

		System.out.println("Nao existe veiculo de passeio com esta placa.");
	}

	private static void excluirCargaPorPlaca(Leitura leitura, BDVeiculo bdVeiculo) throws IOException {
		String placa = leitura.entDados("Informe a placa do veiculo de carga para excluir: ").trim();

		if (bdVeiculo.excluirCarga(placa)) {
			System.out.println("Veiculo de carga excluido com sucesso.");
			return;
		}

		System.out.println("Nao existe veiculo de carga com esta placa.");
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
