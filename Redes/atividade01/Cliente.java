package local.redes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

// @author Caio Mantovani Borba
public class Cliente {

    public static void main(String[] args) {
        try (Scanner teclado = new Scanner(System.in)) {
            System.out.println("Digite um CPF para verificação:");
            if (!teclado.hasNextLine()) {
                System.out.println("Nenhum CPF foi informado. Cliente encerrado.");
                return;
            }
            String cpf = teclado.nextLine();

            System.out.println("Conectando ao servidor em 127.0.0.1:50000...");
            try (Socket conexao = new Socket("127.0.0.1", 50000);
                 DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
                 DataInputStream entrada = new DataInputStream(conexao.getInputStream())) {
                System.out.println("Conexão estabelecida. Enviando CPF...");
                saida.writeUTF(cpf);
                saida.flush();

                System.out.println("Aguardando a validação do servidor...");
                System.out.println(entrada.readUTF());
            }
            System.out.println("Conexão fechada. Cliente encerrado.");
        } catch (IOException e) {
            System.err.println("Erro na comunicação: " + e.getMessage());
            System.err.println("Inicie o Servidor antes do Cliente. Cliente encerrado.");
        }
    }
}
