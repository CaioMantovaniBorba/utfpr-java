package atividade09.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import atividade09.entities.BDVeiculos;
import atividade09.entities.Carga;
import atividade09.entities.Passeio;

class JanelaOperacoes extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JFrame janelaAnterior;
	private final BDVeiculos bdVeiculos;
	private final TipoVeiculo tipoVeiculo;

	JanelaOperacoes(JFrame janelaAnterior, BDVeiculos bdVeiculos, TipoVeiculo tipoVeiculo) {
		this.janelaAnterior = janelaAnterior;
		this.bdVeiculos = bdVeiculos;
		this.tipoVeiculo = tipoVeiculo;

		setTitle("Veiculos de " + tipoVeiculo.getDescricao());
		setSize(440, 330);
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
		JPanel painelBotoes = new JPanel(new GridLayout(6, 1, 10, 10));
		painelBotoes.add(criarBotao("Cadastrar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirCadastro();
			}
		}));
		painelBotoes.add(criarBotao("Imprimir Todos", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirTabela(false);
			}
		}));
		painelBotoes.add(criarBotao("Imprimir pela Placa", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				imprimirPorPlaca();
			}
		}));
		painelBotoes.add(criarBotao("Excluir pela Placa", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				excluirPorPlaca();
			}
		}));
		painelBotoes.add(criarBotao("Excluir Todos", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirTabela(true);
			}
		}));
		painelBotoes.add(criarBotao("Sair", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				voltar();
			}
		}));

		JPanel margem = new JPanel(new BorderLayout());
		margem.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 55, 25, 55));
		margem.add(painelBotoes, BorderLayout.CENTER);
		add(margem, BorderLayout.CENTER);
	}

	private JButton criarBotao(String texto, ActionListener actionListener) {
		JButton botao = new JButton(texto);
		botao.addActionListener(actionListener);
		return botao;
	}

	private void abrirCadastro() {
		setVisible(false);
		new JanelaCadastro(this, bdVeiculos, tipoVeiculo).setVisible(true);
	}

	private void abrirTabela(boolean modoExclusao) {
		setVisible(false);
		new JanelaTabelaVeiculos(this, bdVeiculos, tipoVeiculo, modoExclusao).setVisible(true);
	}

	private void imprimirPorPlaca() {
		String placa = solicitarPlaca("Informe a placa do veiculo de " + tipoVeiculo.getDescricao() + ":");

		if (placa == null) {
			return;
		}

		if (tipoVeiculo == TipoVeiculo.PASSEIO) {
			Passeio passeio = bdVeiculos.buscarPasseio(placa);
			if (passeio == null) {
				mostrarMensagem("Nao existe veiculo de passeio com esta placa.");
				return;
			}
			mostrarMensagem(FormatadorVeiculo.formatar(passeio));
			return;
		}

		Carga carga = bdVeiculos.buscarCarga(placa);
		if (carga == null) {
			mostrarMensagem("Nao existe veiculo de carga com esta placa.");
			return;
		}
		mostrarMensagem(FormatadorVeiculo.formatar(carga));
	}

	private void excluirPorPlaca() {
		String placa = solicitarPlaca("Informe a placa do veiculo de " + tipoVeiculo.getDescricao() + " para excluir:");

		if (placa == null) {
			return;
		}

		boolean excluiu = tipoVeiculo == TipoVeiculo.PASSEIO ? bdVeiculos.excluirPasseio(placa)
				: bdVeiculos.excluirCarga(placa);

		if (excluiu) {
			mostrarMensagem("Veiculo excluido com sucesso.");
			return;
		}

		mostrarMensagem("Nao existe veiculo de " + tipoVeiculo.getDescricao().toLowerCase() + " com esta placa.");
	}

	private String solicitarPlaca(String mensagem) {
		String placa = JOptionPane.showInputDialog(this, mensagem);

		if (placa == null) {
			return null;
		}

		placa = placa.trim();
		if (placa.isEmpty()) {
			mostrarMensagem("A placa deve ser informada.");
			return null;
		}

		return placa;
	}

	private void mostrarMensagem(String mensagem) {
		JOptionPane.showMessageDialog(this, mensagem);
	}

	private void voltar() {
		dispose();
		janelaAnterior.setVisible(true);
	}
}
