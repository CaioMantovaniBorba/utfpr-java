package atividade09.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import atividade09.entities.BDVeiculos;

public class JanelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	private final BDVeiculos bdVeiculos;

	public JanelaPrincipal(BDVeiculos bdVeiculos) {
		this.bdVeiculos = bdVeiculos;

		setTitle("Sistema de Gestao de Veiculos");
		setSize(420, 260);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		criarComponentes();
	}

	private void criarComponentes() {
		JLabel titulo = new JLabel("Sistema de Gestao de Veiculos", SwingConstants.CENTER);
		add(titulo, BorderLayout.NORTH);

		JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 10, 10));
		painelBotoes.add(criarBotaoMenu("Veiculos de Passeio", TipoVeiculo.PASSEIO));
		painelBotoes.add(criarBotaoMenu("Veiculos de Carga", TipoVeiculo.CARGA));

		JButton sair = new JButton("Sair");
		sair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		painelBotoes.add(sair);

		JPanel margem = new JPanel(new BorderLayout());
		margem.setBorder(javax.swing.BorderFactory.createEmptyBorder(25, 45, 25, 45));
		margem.add(painelBotoes, BorderLayout.CENTER);
		add(margem, BorderLayout.CENTER);
	}

	private JButton criarBotaoMenu(String texto, final TipoVeiculo tipoVeiculo) {
		JButton botao = new JButton(texto);
		botao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirJanela(new JanelaOperacoes(JanelaPrincipal.this, bdVeiculos, tipoVeiculo));
			}
		});
		return botao;
	}

	private void abrirJanela(JFrame janela) {
		setVisible(false);
		janela.setVisible(true);
	}
}
