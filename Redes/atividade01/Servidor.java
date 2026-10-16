package local.redes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

// @author Caio Mantovani Borba
public class Servidor {

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(
                50000, 1, InetAddress.getByName("127.0.0.1"))) {
            System.out.println("Servidor iniciado em 127.0.0.1:50000.");
            System.out.println("Aguardando a conexão de um cliente...");

            try (Socket conexao = servidor.accept();
                 DataInputStream entrada = new DataInputStream(conexao.getInputStream());
                 DataOutputStream saida = new DataOutputStream(conexao.getOutputStream())) {
                System.out.println("Cliente conectado. Aguardando o CPF...");
                String cpf = entrada.readUTF();
                System.out.println("CPF recebido. Validando os dígitos verificadores...");

                String resultado = validarCpf(cpf)
                        ? "Este CPF é válido."
                        : "Este CPF é inválido.";
                System.out.println(resultado);
                saida.writeUTF(resultado);
                saida.flush();
                System.out.println("Resultado enviado ao cliente.");
            }
        } catch (IOException e) {
            System.err.println("Erro no servidor: " + e.getMessage());
        } finally {
            System.out.println("Servidor encerrado; conexões e porta liberadas.");
        }
    }

    private static boolean validarCpf(String entrada) {
        String cpf = entrada.trim();
        if (!cpf.matches("[0-9]{11}")
                && !cpf.matches("[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}")) {
            return false;
        }
        cpf = cpf.replace(".", "").replace("-", "");

        if (cpf.equals("00000000000")) {
            return false;
        }

        return conferirDigito(cpf, 9) && conferirDigito(cpf, 10);
    }

    private static boolean conferirDigito(String cpf, int posicao) {
        int soma = 0;
        for (int i = 0; i < posicao; i++) {
            soma += (cpf.charAt(i) - '0') * (posicao + 1 - i);
        }
        int resto = soma % 11;
        int digito = resto < 2 ? 0 : 11 - resto;
        return digito == cpf.charAt(posicao) - '0';
    }
}
