package atividade08.exception;

public class VeicExistException extends Exception {
	private static final long serialVersionUID = 1L;

	public VeicExistException() {
		super("J\u00e1 existe um ve\u00edculo com esta placa");
	}
}
