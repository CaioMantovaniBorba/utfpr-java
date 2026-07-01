// Caio Vinícius Mantovani Borba

package br.edu.utfpr.javai.avaliacao.main;

import br.edu.utfpr.javai.avaliacao.entities.PessoaJuridica;
import br.edu.utfpr.javai.avaliacao.exception.NumException;

public class TstConta {

	public static void main(String[] args) {

		// ENTRADA DE DADOS
		PessoaJuridica pessoaJuridica = new PessoaJuridica();

		try {
			pessoaJuridica.setNumeroConta(12356);
		} catch (NumException e) {
			e.impMsg();
		}
		pessoaJuridica.setCnpj(123456789);
		pessoaJuridica.getEnder().setRua("Rua Javeiro da Silva");

		pessoaJuridica.getResponsavel().setCpf(12);
		pessoaJuridica.getResponsavel().setNome("Caio Borba");

		// SAIDA DE DADOS
		System.out.println(pessoaJuridica.getNumeroConta());
		pessoaJuridica.validar();
		System.out.println(pessoaJuridica.getCnpj());
		System.out.println(pessoaJuridica.getEnder().getRua());

		System.out.println(pessoaJuridica.getResponsavel().getCpf());
		pessoaJuridica.getResponsavel().verifDoc();
		System.out.println(pessoaJuridica.getResponsavel().getNome());
		pessoaJuridica.verifDoc();
	}

}