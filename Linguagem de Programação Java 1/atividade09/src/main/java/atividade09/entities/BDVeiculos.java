package atividade09.entities;

import java.util.ArrayList;
import java.util.List;

import atividade09.exception.VeicExistException;

public class BDVeiculos {
	private List<Passeio> listaPasseio;
	private List<Carga> listaCarga;

	public BDVeiculos() {
		this.listaPasseio = new ArrayList<Passeio>();
		this.listaCarga = new ArrayList<Carga>();
	}

	public List<Passeio> getListaPasseio() {
		return listaPasseio;
	}

	public void setListaPasseio(List<Passeio> listaPasseio) {
		this.listaPasseio = listaPasseio;
	}

	public List<Carga> getListaCarga() {
		return listaCarga;
	}

	public void setListaCarga(List<Carga> listaCarga) {
		this.listaCarga = listaCarga;
	}

	public void adicionarPasseio(Passeio passeio) throws VeicExistException {
		if (existePlaca(passeio.getPlaca())) {
			throw new VeicExistException();
		}

		listaPasseio.add(passeio);
	}

	public void adicionarCarga(Carga carga) throws VeicExistException {
		if (existePlaca(carga.getPlaca())) {
			throw new VeicExistException();
		}

		listaCarga.add(carga);
	}

	public boolean existePlaca(String placa) {
		return buscarPasseio(placa) != null || buscarCarga(placa) != null;
	}

	public Passeio buscarPasseio(String placa) {
		for (Passeio passeio : listaPasseio) {
			if (passeio.getPlaca().equalsIgnoreCase(placa)) {
				return passeio;
			}
		}

		return null;
	}

	public Carga buscarCarga(String placa) {
		for (Carga carga : listaCarga) {
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

		listaPasseio.remove(passeio);
		return true;
	}

	public boolean excluirCarga(String placa) {
		Carga carga = buscarCarga(placa);

		if (carga == null) {
			return false;
		}

		listaCarga.remove(carga);
		return true;
	}

	public void excluirTodosPasseio() {
		listaPasseio.clear();
	}

	public void excluirTodosCarga() {
		listaCarga.clear();
	}
}
