package local.redes;

import java.io.Serializable;

// @author Caio Mantovani Borba
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private int idade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
