package local.redes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// @author Caio Mantovani Borba
public final class FormCliente extends JPanel {
    private static final long serialVersionUID = 1L;

    private final JTextField campoNome = new JTextField(35);
    private final JTextField campoIdade = new JTextField(35);
    private final JTextArea retornoServidor = new JTextArea(6, 35);

    public FormCliente() {
        super(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel campos = new JPanel(new GridBagLayout());
        GridBagConstraints posicao = new GridBagConstraints();
        posicao.gridx = 0;
        posicao.weightx = 1;
        posicao.fill = GridBagConstraints.HORIZONTAL;
        posicao.insets = new Insets(0, 0, 8, 0);

        posicao.gridy = 0;
        JLabel nome = new JLabel("Nome");
        nome.setLabelFor(campoNome);
        campos.add(nome, posicao);
        posicao.gridy = 1;
        campos.add(campoNome, posicao);
        posicao.gridy = 2;
        JLabel idade = new JLabel("Idade");
        idade.setLabelFor(campoIdade);
        campos.add(idade, posicao);
        posicao.gridy = 3;
        campos.add(campoIdade, posicao);
        posicao.gridy = 4;
        JLabel retorno = new JLabel("Retorno do Servidor");
        retorno.setLabelFor(retornoServidor);
        campos.add(retorno, posicao);

        retornoServidor.setEditable(false);
        retornoServidor.setLineWrap(true);
        retornoServidor.setWrapStyleWord(true);
        posicao.gridy = 5;
        posicao.weighty = 1;
        posicao.fill = GridBagConstraints.BOTH;
        campos.add(new JScrollPane(retornoServidor), posicao);
        add(campos, BorderLayout.CENTER);

        JButton enviar = new JButton("Enviar");
        enviar.addActionListener(evento -> enviarPessoa());
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        botoes.add(enviar);
        add(botoes, BorderLayout.SOUTH);
    }

    private void enviarPessoa() {
        String nome = campoNome.getText().trim();
        int idade;
        try {
            idade = Integer.parseInt(campoIdade.getText().trim());
            if (nome.isEmpty() || idade < 0) {
                retornoServidor.setText("Informe o nome e uma idade inteira não negativa.");
                return;
            }
        } catch (NumberFormatException e) {
            retornoServidor.setText("Informe uma idade inteira não negativa.");
            return;
        }

        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setIdade(idade);

        // Envio síncrono
        try (Socket conexao = new Socket()) {
            conexao.connect(new InetSocketAddress("127.0.0.1", 50000), 5000);
            conexao.setSoTimeout(10000);
            try (ObjectOutputStream saida = new ObjectOutputStream(conexao.getOutputStream());
                 DataInputStream entrada = new DataInputStream(conexao.getInputStream())) {
                saida.writeObject(pessoa);
                saida.flush();
                String resposta = entrada.readUTF();
                retornoServidor.setText("Recebeu do servidor:\n" + resposta);
            }
        } catch (IOException e) {
            retornoServidor.setText("Não foi possível transmitir os dados.\n"
                    + "Verifique se o Servidor foi iniciado.\n" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame janela = new JFrame("Cliente - Envio de Pessoa");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setContentPane(new FormCliente());
            janela.pack();
            janela.setLocationRelativeTo(null);
            janela.setVisible(true);
        });
    }
}
