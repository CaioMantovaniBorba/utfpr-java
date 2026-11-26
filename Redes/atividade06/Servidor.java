package local.redes;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.Map;

// @author Caio Mantovani Borba
public class Servidor implements Apuracao {
    private final Map<String, Long> votos = new LinkedHashMap<>();

    public Servidor() {
        for (String candidato : new String[]{"João", "Maria", "Vitor", "Paulo"}) {
            votos.put(candidato, 0L);
        }
    }

    @Override
    public synchronized String[] listarCandidatos() {
        return votos.keySet().toArray(new String[0]);
    }

    @Override
    public synchronized void registrarVotos(String candidato, int quantidade) {
        if (!votos.containsKey(candidato) || quantidade <= 0) {
            throw new IllegalArgumentException("Informe um candidato da lista e uma quantidade positiva.");
        }
        votos.put(candidato, Math.addExact(votos.get(candidato), quantidade));
    }

    private synchronized void exibirApuracao() {
        System.out.println("\nVotos apurados:");
        votos.forEach((nome, total) -> System.out.printf("%s: %d votos%n", nome, total));
    }

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("java.rmi.server.hostname", ENDERECO);
        try {
            Registry registro = LocateRegistry.createRegistry(PORTA);
            Servidor servidor = new Servidor();
            Apuracao servico = (Apuracao) UnicastRemoteObject.exportObject(servidor, 0);
            registro.rebind(SERVICO, servico);
            System.out.printf("Servidor de apuração disponível em %s:%d%n", ENDERECO, PORTA);
            while (true) {
                servidor.exibirApuracao();
                Thread.sleep(5000);
            }
        } catch (RemoteException erro) {
            System.err.println("Falha ao iniciar o servidor: " + erro.getMessage());
        }
    }
}
