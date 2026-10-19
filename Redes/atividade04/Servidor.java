package local.redes;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

// @author Caio Mantovani Borba
public class Servidor implements Runnable {
    private final Socket conexao;

    private Servidor(Socket conexao) {
        this.conexao = conexao;
    }

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(
                50000, 50, InetAddress.getByName("127.0.0.1"))) {
            System.out.println("Servidor iniciado em 127.0.0.1:50000.");
            int numeroCliente = 0;
            while (true) {
                System.out.println("Aguardando clientes...");
                Socket cliente = servidor.accept();
                Thread atendimento = new Thread(new Servidor(cliente),
                        "Cliente-" + (++numeroCliente));
                atendimento.start();
            }
        } catch (IOException e) {
            System.err.println("Não foi possível manter o servidor: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        String identificacao = Thread.currentThread().getName();
        try (Socket cliente = conexao) {
            cliente.setSoTimeout(10000);
            try (ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                 DataOutputStream saida = new DataOutputStream(cliente.getOutputStream())) {
                Object recebido = entrada.readObject();
                if (!(recebido instanceof Pessoa)) {
                    throw new IOException("O objeto recebido não é uma Pessoa.");
                }
                Pessoa pessoa = (Pessoa) recebido;
                System.out.println("[" + identificacao + "] Pessoa recebida:\n"
                        + "Nome: " + pessoa.getNome() + "\n"
                        + "Idade: " + pessoa.getIdade());

                saida.writeUTF("Dados recebidos corretamente!");
                saida.flush();
                System.out.println("[" + identificacao + "] Confirmação enviada ao cliente.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[" + identificacao + "] Não foi possível receber os dados: "
                    + e.getMessage());
        }
        // Fecha apenas o socket deste atendimento
        // O ServerSocket continua aberto
    }
}
