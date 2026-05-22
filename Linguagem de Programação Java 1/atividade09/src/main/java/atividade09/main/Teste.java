package atividade09.main;

import java.awt.EventQueue;

import atividade09.entities.BDVeiculos;
import atividade09.gui.JanelaPrincipal;

public class Teste {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JanelaPrincipal janelaPrincipal = new JanelaPrincipal(new BDVeiculos());
				janelaPrincipal.setVisible(true);
			}
		});
	}
}
