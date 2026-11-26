package local.redes;

import java.rmi.Remote;
import java.rmi.RemoteException;

// @author Caio Mantovani Borba
public interface Apuracao extends Remote {
    String ENDERECO = "127.0.0.1";
    int PORTA = 1099;
    String SERVICO = "Apuracao";

    String[] listarCandidatos() throws RemoteException;
    void registrarVotos(String candidato, int quantidade) throws RemoteException;
}
