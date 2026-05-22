package atividade09.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import atividade09.entities.BDVeiculos;
import atividade09.entities.Carga;
import atividade09.entities.Passeio;
import atividade09.entities.Veiculo;
import atividade09.exception.VeicExistException;
import atividade09.exception.VelocException;

class JanelaCadastro extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JFrame janelaAnterior;
	private final BDVeiculos bdVeiculos;
	private final TipoVeiculo tipoVeiculo;

	private JTextField placa;
	private JTextField marca;
	private JTextField modelo;
	private JTextField cor;
	private JTextField velocMax;
	private JTextField qtdRodas;
	private JTextField qtdPist;
	private JTextField potencia;
	private JTextField qtdPassageiros;
	private JTextField cargaMax;
	private JTextField tara;

	JanelaCadastro(JFrame janelaAnterior, BDVeiculos bdVeiculos, TipoVeiculo tipoVeiculo) {
		this.janelaAnterior = janelaAnterior;
		this.bdVeiculos = bdVeiculos;
		this.tipoVeiculo = tipoVeiculo;

		setTitle("Cadastrar Veiculo de " + tipoVeiculo.getDescricao());
		setSize(520, tipoVeiculo == TipoVeiculo.PASSEIO ? 450 : 490);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				voltar();
			}
		});
		criarComponentes();
	}

	private void criarComponentes() {
		JPanel formulario = new JPanel(new GridLayout(0, 2, 8, 8));
		formulario.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 25, 10, 25));

		placa = adicionarCampo(formulario, "Placa:");
		marca = adicionarCampo(formulario, "Marca:");
		modelo = adicionarCampo(formulario, "Modelo:");
		cor = adicionarCampo(formulario, "Cor:");
		velocMax = adicionarCampo(formulario, "Velocidade maxima em Km/h:");
		qtdRodas = adicionarCampo(formulario, "Quantidade de rodas:");
		qtdPist = adicionarCampo(formulario, "Quantidade de pistoes do motor:");
		potencia = adicionarCampo(formulario, "Potencia do motor:");

		if (tipoVeiculo == TipoVeiculo.PASSEIO) {
			qtdPassageiros = adicionarCampo(formulario, "Quantidade de passageiros:");
		} else {
			cargaMax = adicionarCampo(formulario, "Carga maxima:");
			tara = adicionarCampo(formulario, "Tara:");
		}

		JPanel botoes = new JPanel(new GridLayout(1, 2, 10, 10));
		JButton cadastrar = new JButton("Cadastrar");
		cadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cadastrarVeiculo();
			}
		});
		JButton sair = new JButton("Sair");
		sair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				voltar();
			}
		});
		botoes.add(cadastrar);
		botoes.add(sair);

		JPanel rodape = new JPanel(new BorderLayout());
		rodape.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 25, 20, 25));
		rodape.add(botoes, BorderLayout.CENTER);

		add(formulario, BorderLayout.CENTER);
		add(rodape, BorderLayout.SOUTH);
	}

	private JTextField adicionarCampo(JPanel formulario, String rotulo) {
		JTextField campo = new JTextField();
		formulario.add(new JLabel(rotulo));
		formulario.add(campo);
		return campo;
	}

	private void cadastrarVeiculo() {
		try {
			if (tipoVeiculo == TipoVeiculo.PASSEIO) {
				bdVeiculos.adicionarPasseio(criarPasseio());
			} else {
				bdVeiculos.adicionarCarga(criarCarga());
			}

			mostrarMensagem("Veiculo cadastrado com sucesso.");
			limparCampos();
		} catch (VeicExistException e) {
			mostrarMensagem(e.getMessage());
		} catch (IllegalArgumentException e) {
			mostrarMensagem(e.getMessage());
		}
	}

	private Passeio criarPasseio() {
		Passeio passeio = new Passeio();
		preencherDadosComuns(passeio);
		passeio.setQtdPassageiros(lerInteiro(qtdPassageiros, "Quantidade de passageiros"));
		return passeio;
	}

	private Carga criarCarga() {
		Carga carga = new Carga();
		preencherDadosComuns(carga);
		carga.setCargaMax(lerInteiro(cargaMax, "Carga maxima"));
		carga.setTara(lerInteiro(tara, "Tara"));
		return carga;
	}

	private void preencherDadosComuns(Veiculo veiculo) {
		veiculo.setPlaca(lerTextoObrigatorio(placa, "Placa"));
		veiculo.setMarca(lerTextoObrigatorio(marca, "Marca"));
		veiculo.setModelo(lerTextoObrigatorio(modelo, "Modelo"));
		veiculo.setCor(lerTextoObrigatorio(cor, "Cor"));
		veiculo.setVelocMax(lerVelocidade());
		veiculo.setQtdRodas(lerInteiro(qtdRodas, "Quantidade de rodas"));
		veiculo.getMotor().setQtdPist(lerInteiro(qtdPist, "Quantidade de pistoes do motor"));
		veiculo.getMotor().setPotencia(lerInteiro(potencia, "Potencia do motor"));
	}

	private String lerTextoObrigatorio(JTextField campo, String nomeCampo) {
		String valor = campo.getText().trim();

		if (valor.isEmpty()) {
			throw new IllegalArgumentException(nomeCampo + " deve ser informado.");
		}

		return valor;
	}

	private int lerInteiro(JTextField campo, String nomeCampo) {
		try {
			return Integer.parseInt(lerTextoObrigatorio(campo, nomeCampo));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(nomeCampo + " deve ser um numero inteiro valido.");
		}
	}

	private float lerVelocidade() {
		float velocidade;

		try {
			velocidade = Float.parseFloat(lerTextoObrigatorio(velocMax, "Velocidade maxima"));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Velocidade maxima deve ser um numero valido.");
		}

		if (velocidade < 80 || velocidade > 110) {
			mostrarMensagem(new VelocException().getMessage() + "\nSera usada a velocidade padrao de "
					+ tipoVeiculo.getVelocidadePadrao() + " Km/h.");
			return tipoVeiculo.getVelocidadePadrao();
		}

		return velocidade;
	}

	private void limparCampos() {
		placa.setText("");
		marca.setText("");
		modelo.setText("");
		cor.setText("");
		velocMax.setText("");
		qtdRodas.setText("");
		qtdPist.setText("");
		potencia.setText("");

		if (tipoVeiculo == TipoVeiculo.PASSEIO) {
			qtdPassageiros.setText("");
		} else {
			cargaMax.setText("");
			tara.setText("");
		}

		placa.requestFocus();
	}

	private void mostrarMensagem(String mensagem) {
		JOptionPane.showMessageDialog(this, mensagem);
	}

	private void voltar() {
		dispose();
		janelaAnterior.setVisible(true);
	}
}
