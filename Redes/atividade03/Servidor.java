package local.redes;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

// @author Caio Mantovani Borba
public class Servidor {
    public static void main(String[] args) {
        // Atende uma conexão e encerra
        try (ServerSocket servidor = new ServerSocket(
                50000, 1, InetAddress.getByName("127.0.0.1"))) {
            System.out.println("Servidor iniciado em 127.0.0.1:50000.");
            System.out.println("Aguardando o cliente...");

            try (Socket conexao = servidor.accept()) {
                conexao.setSoTimeout(10000);
                try (ObjectInputStream entrada = new ObjectInputStream(conexao.getInputStream());
                     DataOutputStream saida = new DataOutputStream(conexao.getOutputStream())) {
                    Pessoa pessoa = (Pessoa) entrada.readObject();
                    System.out.println("Pessoa recebida:");
                    System.out.println("Nome: " + pessoa.getNome());
                    System.out.println("Idade: " + pessoa.getIdade());

                    saida.writeUTF("Dados recebidos corretamente!");
                    saida.flush();
                    System.out.println("Confirmação enviada ao cliente.");
                }
            }
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.err.println("Não foi possível receber os dados: " + e.getMessage());
        }
        System.out.println("Servidor encerrado.");
    }
}
