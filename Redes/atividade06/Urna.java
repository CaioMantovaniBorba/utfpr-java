package local.redes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

// @author Caio Mantovani Borba
public class Urna extends JFrame {
    private final Apuracao servidor;
    private final JComboBox<String> candidatos;
    private final JTextField quantidade = new JTextField(10);
    private final JButton enviar = new JButton("Enviar ao servidor central");
    private final JLabel aviso = new JLabel("Selecione o candidato e informe os votos da urna.");

    private Urna(Apuracao servidor, String[] nomes) {
        super("Urna eletrônica");
        this.servidor = servidor;
        candidatos = new JComboBox<>(nomes);
        JPanel campos = new JPanel(new GridLayout(2, 2, 12, 4));
        campos.add(new JLabel("Candidato"));
        campos.add(new JLabel("Quantidade de votos"));
        campos.add(candidatos);
        campos.add(quantidade);

        JPanel painel = new JPanel(new BorderLayout(0, 12));
        painel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        painel.add(campos, BorderLayout.NORTH);
        painel.add(enviar, BorderLayout.CENTER);
        painel.add(aviso, BorderLayout.SOUTH);
        setContentPane(painel);
        enviar.addActionListener(evento -> enviarVotos());
        getRootPane().setDefaultButton(enviar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void enviarVotos() {
        int votos;
        try {
            votos = Integer.parseInt(quantidade.getText().trim());
            if (votos <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException erro) {
            aviso.setText("Informe uma quantidade inteira maior que zero.");
            quantidade.requestFocusInWindow();
            return;
        }
        String nome = (String) candidatos.getSelectedItem();
        enviar.setEnabled(false);
        quantidade.setEnabled(false);
        candidatos.setEnabled(false);
        aviso.setText("Enviando votos...");
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws RemoteException {
                servidor.registrarVotos(nome, votos);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    aviso.setText("Votos registrados para " + nome + ": " + votos);
                    quantidade.setText("");
                } catch (ExecutionException erro) {
                    aviso.setText("Envio sem confirmação. Confira a apuração no servidor.");
                } catch (InterruptedException erro) {
                    Thread.currentThread().interrupt();
                    aviso.setText("A confirmação do envio foi interrompida.");
                } finally {
                    enviar.setEnabled(true);
                    quantidade.setEnabled(true);
                    candidatos.setEnabled(true);
                    quantidade.requestFocusInWindow();
                }
            }
        }.execute();
    }

    public static void main(String[] args) {
        try {
            Apuracao servidor = (Apuracao) LocateRegistry.getRegistry(Apuracao.ENDERECO, Apuracao.PORTA)
                    .lookup(Apuracao.SERVICO);
            String[] nomes = servidor.listarCandidatos();
            SwingUtilities.invokeLater(() -> new Urna(servidor, nomes).setVisible(true));
        } catch (RemoteException | NotBoundException erro) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "Não foi possível conectar. Inicie o servidor de apuração e abra a urna novamente.",
                    "Urna eletrônica", JOptionPane.ERROR_MESSAGE));
        }
    }
}
