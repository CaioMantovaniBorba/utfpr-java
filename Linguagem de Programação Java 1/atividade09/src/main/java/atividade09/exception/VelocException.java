package atividade09.exception;

public class VelocException extends Exception {
	private static final long serialVersionUID = 1L;

	public VelocException() {
		super("A velocidade m\u00e1xima est\u00e1 fora dos limites brasileiros");
	}
}
