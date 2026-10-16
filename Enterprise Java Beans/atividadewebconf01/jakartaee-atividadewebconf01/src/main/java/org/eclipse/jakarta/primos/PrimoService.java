package org.eclipse.jakarta.primos;

import jakarta.ejb.Stateless;

// .\mvnw.cmd clean package cargo:run
// http://localhost:8080/jakartaee-atividadewebconf01/rest/primos?numero=8

@Stateless
public class PrimoService {

    public long proximoPrimo(int numero) {
        if (numero < 2) {
            return 2;
        }
        long candidato = (long) numero + 1;
        if (candidato % 2 == 0) {
            candidato++;
        }
        while (!ehPrimo(candidato)) {
            candidato += 2;
        }
        return candidato;
    }

    private boolean ehPrimo(long numero) {
        for (long divisor = 3; divisor <= numero / divisor; divisor += 2) {
            if (numero % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
