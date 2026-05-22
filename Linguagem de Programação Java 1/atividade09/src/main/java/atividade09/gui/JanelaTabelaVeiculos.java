package atividade09.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import atividade09.entities.BDVeiculos;
import atividade09.entities.Carga;
import atividade09.entities.Passeio;

class JanelaTabelaVeiculos extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JFrame janelaAnterior;
	private final BDVeiculos bdVeiculos;
	private final TipoVeiculo tipoVeiculo;
	private final boolean modoExclusao;
	private JTable tabela;
	private DefaultTableModel modelo;

	JanelaTabelaVeiculos(JFrame janelaAnterior, BDVeiculos bdVeiculos, TipoVeiculo tipoVeiculo, boolean modoExclusao) {
		this.janelaAnterior = janelaAnterior;
		this.bdVeiculos = bdVeiculos;
		this.tipoVeiculo = tipoVeiculo;
		this.modoExclusao = modoExclusao;

		setTitle((modoExclusao ? "Excluir Todos - " : "Imprimir Todos - ") + tipoVeiculo.getDescricao());
		setSize(900, 420);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				voltar();
			}
		});
		criarComponentes();
		carregarTabela();
	}

	private void criarComponentes() {
		modelo = new DefaultTableModel(criarColunas(), 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabela = new JTable(modelo);
		tabela.setAutoCreateRowSorter(true);
		add(new JScrollPane(tabela), BorderLayout.CENTER);

		JPanel botoes = new JPanel();

		if (modoExclusao) {
			JButton excluirSelecionado = new JButton("Excluir Selecionado");
			excluirSelecionado.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					excluirSelecionado();
				}
			});
			JButton excluirTodos = new JButton("Excluir Todos");
			excluirTodos.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					excluirTodos();
				}
			});
			botoes.add(excluirSelecionado);
			botoes.add(excluirTodos);
		}

		JButton sair = new JButton("Sair");
		sair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				voltar();
			}
		});
		botoes.add(sair);
		add(botoes, BorderLayout.SOUTH);
	}

	private String[] criarColunas() {
		if (tipoVeiculo == TipoVeiculo.PASSEIO) {
			return new String[] { "Placa", "Marca", "Modelo", "Cor", "Velocidade", "Rodas", "Pistoes", "Potencia",
					"Passageiros", "Calcular", "Velocidade m/h" };
		}

		return new String[] { "Placa", "Marca", "Modelo", "Cor", "Velocidade", "Rodas", "Pistoes", "Potencia",
				"Carga Max", "Tara", "Calcular", "Velocidade cm/h" };
	}

	private void carregarTabela() {
		modelo.setRowCount(0);

		if (tipoVeiculo == TipoVeiculo.PASSEIO) {
			for (Passeio passeio : bdVeiculos.getListaPasseio()) {
				modelo.addRow(new Object[] { passeio.getPlaca(), passeio.getMarca(), passeio.getModelo(),
						passeio.getCor(), passeio.getVelocMax(), passeio.getQtdRodas(),
						passeio.getMotor().getQtdPist(), passeio.getMotor().getPotencia(),
						passeio.getQtdPassageiros(), passeio.calcular(), passeio.calcVel(passeio.getVelocMax()) });
			}
			return;
		}

		for (Carga carga : bdVeiculos.getListaCarga()) {
			modelo.addRow(new Object[] { carga.getPlaca(), carga.getMarca(), carga.getModelo(), carga.getCor(),
					carga.getVelocMax(), carga.getQtdRodas(), carga.getMotor().getQtdPist(),
					carga.getMotor().getPotencia(), carga.getCargaMax(), carga.getTara(), carga.calcular(),
					carga.calcVel(carga.getVelocMax()) });
		}
	}

	private void excluirSelecionado() {
		int linhaSelecionada = tabela.getSelectedRow();

		if (linhaSelecionada < 0) {
			mostrarMensagem("Selecione um veiculo na tabela.");
			return;
		}

		int linhaModelo = tabela.convertRowIndexToModel(linhaSelecionada);
		String placa = String.valueOf(modelo.getValueAt(linhaModelo, 0));

		if (tipoVeiculo == TipoVeiculo.PASSEIO) {
			bdVeiculos.excluirPasseio(placa);
		} else {
			bdVeiculos.excluirCarga(placa);
		}

		carregarTabela();
		mostrarMensagem("Veiculo excluido com sucesso.");
	}

	private void excluirTodos() {
		if (modelo.getRowCount() == 0) {
			mostrarMensagem("Nao ha veiculos cadastrados.");
			return;
		}

		int resposta = JOptionPane.showConfirmDialog(this,
				"Deseja excluir todos os veiculos de " + tipoVeiculo.getDescricao().toLowerCase() + "?",
				"Confirmar exclusao", JOptionPane.YES_NO_OPTION);

		if (resposta != JOptionPane.YES_OPTION) {
			return;
		}

		if (tipoVeiculo == TipoVeiculo.PASSEIO) {
			bdVeiculos.excluirTodosPasseio();
		} else {
			bdVeiculos.excluirTodosCarga();
		}

		carregarTabela();
		mostrarMensagem("Todos os veiculos foram excluidos.");
	}

	private void mostrarMensagem(String mensagem) {
		JOptionPane.showMessageDialog(this, mensagem);
	}

	private void voltar() {
		dispose();
		janelaAnterior.setVisible(true);
	}
}
