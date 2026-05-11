package atividade08.entities;

import java.util.ArrayList;
import java.util.List;

public class BDVeiculo {
	private List<Passeio> BDPas = new ArrayList<Passeio>();
	private List<Carga> BDCarg = new ArrayList<Carga>();

	public List<Passeio> getBDPas() {
		return BDPas;
	}

	public List<Carga> getBDCarg() {
		return BDCarg;
	}

	public void adicionarPasseio(Passeio passeio) {
		BDPas.add(passeio);
	}

	public void adicionarCarga(Carga carga) {
		BDCarg.add(carga);
	}

	public Passeio buscarPasseio(String placa) {
		for (Passeio passeio : BDPas) {
			if (passeio.getPlaca().equalsIgnoreCase(placa)) {
				return passeio;
			}
		}

		return null;
	}

	public Carga buscarCarga(String placa) {
		for (Carga carga : BDCarg) {
			if (carga.getPlaca().equalsIgnoreCase(placa)) {
				return carga;
			}
		}

		return null;
	}

	public boolean excluirPasseio(String placa) {
		Passeio passeio = buscarPasseio(placa);

		if (passeio == null) {
			return false;
		}

		BDPas.remove(passeio);
		return true;
	}

	public boolean excluirCarga(String placa) {
		Carga carga = buscarCarga(placa);

		if (carga == null) {
			return false;
		}

		BDCarg.remove(carga);
		return true;
	}
}
