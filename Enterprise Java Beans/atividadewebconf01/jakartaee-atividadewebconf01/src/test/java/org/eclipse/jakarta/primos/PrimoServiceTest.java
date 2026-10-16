package org.eclipse.jakarta.primos;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PrimoServiceTest {

    @ParameterizedTest
    @CsvSource({
        "6, 7", "8, 11", "7, 11", "2, 3", "3, 5",
        "-2147483648, 2", "-10, 2", "0, 2", "1, 2",
        "24, 29", "48, 53", "120, 127",
        "2147483646, 2147483647", "2147483647, 2147483659"
    })
    void retornaPrimeiroPrimoEstritamenteMaior(int numero, long esperado) {
        assertEquals(esperado, new PrimoService().proximoPrimo(numero));
    }
}
