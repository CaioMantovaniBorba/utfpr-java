package atividade04.main;

import java.io.IOException;

import atividade04.entities.Carga;
import atividade04.entities.Passeio;
import atividade04.utils.Leitura;

// mvn exec:java -Dexec.mainClass="atividade04.main.Teste"

public class Teste {

	public static void main(String[] args) {
		Leitura scan = new Leitura();

		try {
			menuInicial(scan);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void menuInicial(Leitura scan) throws IOException {
		Passeio[] veiculosPasseio = new Passeio[5];

		Carga[] veiculosCarga = new Carga[5];

		int input = 0;

		while (input != 7) {
			System.out.println("========== MENU INICIAL ==========\n");
			System.out.println("Sistema de Gestão de Veículos\n");
			System.out.println("  1. Cadastrar Veículo de Passeio");
			System.out.println("  2. Cadastrar Veículo de Carga");
			System.out.println("  3. Imprimir Todos os Veículos de Passeio");
			System.out.println("  4. Imprimir Todos os Veículos de Carga");
			System.out.println("  5. Imprimir Veículo de Passeio pela Placa");
			System.out.println("  6. Imprimir Veículo de Carga pela Placa");
			System.out.println("  7. Sair");

			System.out.println("\n==========");

			try {
				input = Integer.parseInt(scan.entDados("Digite uma das opções acima: "));
				opcoesMenu(scan, input, veiculosPasseio, veiculosCarga);
			} catch (NumberFormatException e) {
				System.out.println("A opção escolhida deve ser um número inteiro.");
				scan.entDados("");
				continue;
			}
		}
	}

	public static void opcoesMenu(Leitura scan, int opcao, Passeio[] veiculosPasseio, Carga[] veiculosCarga) {
		switch (opcao) {
		case 1:
			veiculosPasseio = cadastraVeiculoPasseio(scan, veiculosPasseio);
			break;
		case 2:
			veiculosCarga = cadastraVeiculoCarga(scan, veiculosCarga);
			break;
		case 3:
			imprimeVeiculosPasseio(veiculosPasseio);
			break;
		case 4:

			imprimeVeiculosCarga(veiculosCarga);
			break;
		case 5:
			imprimeVeiculosPasseioPelaPlaca(veiculosPasseio, scan);
			break;
		case 6:
			imprimeVeiculosCargaPelaPlaca(veiculosCarga, scan);
			break;
		case 7:
			break;
		default:
			System.out.println("Opção inválida. Escolha uma opção de 1 a 7.");
		}

	}

	public static Passeio[] cadastraVeiculoPasseio(Leitura scan, Passeio[] veiculosPasseio) {
		String input = "";

		do {
			System.out.println("========== CADASTRO DE VEICULO PASSEIO ===========");
			System.out.println("Insira os dados e valores solicitados abaixo: ");
			System.out.println("==========");

			try {
				String inputPlaca = scan.entDados("Placa: ");
				Passeio veiculo = buscaVeiculosPasseio(veiculosPasseio, inputPlaca);

				if (veiculo == null) {
					veiculo = new Passeio();

					veiculo.setPlaca(inputPlaca);
					veiculo.setMarca(scan.entDados("Marca: "));
					veiculo.setModelo(scan.entDados("Modelo: "));
					veiculo.setCor(scan.entDados("Cor: "));
					veiculo.setQtdRodas(Integer.parseInt(scan.entDados("Quantidade de Rodas: ")));
					veiculo.setQtdPassageiros(Integer.parseInt(scan.entDados("Capacidade de Passageiros: ")));
					veiculo.setVelocMax(Integer.parseInt(scan.entDados("Velocidade Máxima: ")));
					veiculo.getMotor().setPotencia(Integer.parseInt(scan.entDados("Potência do Motor: ")));
					veiculo.getMotor().setQtdPist(Integer.parseInt(scan.entDados("Quantidade de Pistões do Motor: ")));

					for (int i = 0; i < veiculosPasseio.length; i++) {
						if (veiculosPasseio[i] == null) {
							veiculosPasseio[i] = veiculo;
							break;
						}
					}
				} else {
					System.out.println("Este veículo já está cadastrado.");
					return veiculosPasseio;
				}

				input = scan.entDados("Deseja adicionar mais veículos de passeio? (SIM/NAO)");

				if (input.equals("SIM") && isPasseioCheio(veiculosPasseio)) {
					System.out.println("\nA lista de veículos de passeio está cheia.\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (input.equals("SIM") && !isPasseioCheio(veiculosPasseio));

		return veiculosPasseio;
	}

	public static Carga[] cadastraVeiculoCarga(Leitura scan, Carga[] veiculosCarga) {
		String input = "";
		do {

			System.out.println("========== CADASTRO DE VEICULO CARGA ==========");
			System.out.println("Insira os dados e valores solicitados abaixo: ");
			System.out.println("==========");

			try {
				String inputPlaca = scan.entDados("Placa: ");
				Carga veiculo = buscaVeiculosCarga(veiculosCarga, inputPlaca);

				if (veiculo == null) {
					veiculo = new Carga();

					veiculo.setPlaca(inputPlaca);
					veiculo.setMarca(scan.entDados("Marca: "));
					veiculo.setModelo(scan.entDados("Modelo: "));
					veiculo.setCor(scan.entDados("Cor: "));
					veiculo.setVelocMax(Integer.parseInt(scan.entDados("Velocidade Máxima: ")));
					veiculo.setQtdRodas(Integer.parseInt(scan.entDados("Quantidade de Rodas: ")));
					veiculo.getMotor().setPotencia(Integer.parseInt(scan.entDados("Potência do Motor: ")));
					veiculo.getMotor().setQtdPist(Integer.parseInt(scan.entDados("Quantidade de Pistões do Motor: ")));
					veiculo.setCargaMax(Integer.parseInt(scan.entDados("Carga Máxima: ")));
					veiculo.setTara(Integer.parseInt(scan.entDados("Quantidade de Tara: ")));

					for (int i = 0; i < veiculosCarga.length; i++) {
						if (veiculosCarga[i] == null) {
							veiculosCarga[i] = veiculo;
							break;
						}
					}
				} else {
					System.out.println("Este veículo já está cadastrado.");
					return veiculosCarga;
				}

				input = scan.entDados("Deseja adicionar mais veículos de carga? (SIM/NAO)");

				if (input.equals("SIM") && isCargaCheio(veiculosCarga)) {
					System.out.println("\nA lista de veículos de carga está cheia.\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} while (input.equals("SIM") && !isCargaCheio(veiculosCarga));

		return veiculosCarga;
	}

	public static void imprimeVeiculosPasseio(Passeio[] veiculosPasseio) {
		System.out.println("========== VEICULOS DE PASSEIO ==========");
		for (Passeio veiculo : veiculosPasseio) {
			if (veiculo != null) {
				System.out.println(veiculo.toString());
				System.out.println("==========");
			}
		}
	}

	public static void imprimeVeiculosCarga(Carga[] veiculosCarga) {
		System.out.println("\n========== VEICULOS DE CARGA ==========");
		for (Carga veiculo : veiculosCarga) {
			if (veiculo != null) {
				System.out.println(veiculo.toString());
				System.out.println("==========");
			}
		}
	}

	public static void imprimeVeiculosPasseioPelaPlaca(Passeio[] veiculosPasseio, Leitura scan) {
		String inputPlaca;
		try {
			inputPlaca = scan.entDados("Placa: ");
			Passeio veiculo = buscaVeiculosPasseio(veiculosPasseio, inputPlaca);

			System.out.println("========== VEICULO DE PASSEIO ==========");
			System.out.println(veiculo.toString());
			System.out.println("==========");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void imprimeVeiculosCargaPelaPlaca(Carga[] veiculosCarga, Leitura scan) {

		String inputPlaca;
		try {
			inputPlaca = scan.entDados("Placa: ");
			Carga veiculo = buscaVeiculosCarga(veiculosCarga, inputPlaca);

			System.out.println("========== VEICULO DE CARGA ==========");
			System.out.println(veiculo.toString());
			System.out.println("==========");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static boolean isPasseioCheio(Passeio[] veiculosPasseio) {
		for (Passeio veiculo : veiculosPasseio) {
			if (veiculo == null) {
				return false;
			}
		}

		return true;
	}

	private static boolean isCargaCheio(Carga[] veiculosCarga) {
		for (Carga veiculo : veiculosCarga) {
			if (veiculo == null) {
				return false;
			}
		}

		return true;
	}

	private static Passeio buscaVeiculosPasseio(Passeio[] veiculosPasseio, String input) {
		for (int i = 0; i < veiculosPasseio.length; i++) {
			if (veiculosPasseio[i] == null) {
				break;
			} else if (veiculosPasseio[i].getPlaca().equals(input)) {
				veiculosPasseio[i].setPlaca(input);
				return veiculosPasseio[i];
			}
		}
		return null;
	}

	private static Carga buscaVeiculosCarga(Carga[] veiculosCarga, String input) {
		for (int i = 0; i < veiculosCarga.length; i++) {
			if (veiculosCarga[i] == null) {
				break;
			} else if (veiculosCarga[i].getPlaca().equals(input)) {
				veiculosCarga[i].setPlaca(input);
				return veiculosCarga[i];
			}
		}
		return null;
	}

}