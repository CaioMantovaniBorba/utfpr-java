# Próximo número primo com EJB

Aplicação Jakarta EE 11 com Java 21 e Payara. Recebe um inteiro e informa o menor número primo **estritamente maior** que ele.

## Executar

Com JDK 21 instalado e `JAVA_HOME` configurado, execute no PowerShell:

```powershell
.\mvnw.cmd clean package cargo:run
```

No Linux/macOS: `./mvnw clean package cargo:run`.
O Maven Wrapper baixa o Maven, as dependências e o Payara na primeira execução.

Acesse [a aplicação](http://localhost:8080/jakartaee-atividadewebconf01/), digite um inteiro e clique em **Calcular próximo primo**.

## Estrutura

- `PrimoService`: EJB `@Stateless` com a regra de cálculo. Testa candidatos ímpares e divisores até a raiz quadrada.
- `PrimoResource`: cliente do EJB via injeção `@EJB`, expõe `GET rest/primos?numero=8`.
- `index.html`: cliente web que chama o serviço REST e apresenta o resultado.

Exemplo de resposta JSON:

```json
{"numero":8,"proximoPrimo":11}
```

Entradas ausentes, fracionárias, inválidas ou fora do intervalo de `int` retornam HTTP 400 com uma mensagem no campo `erro`.
Para negativos, zero e um, o resultado é 2. Se a entrada já é prima, a busca continua até o próximo primo. O retorno é `long` para suportar o próximo primo após `2147483647`, que é `2147483659`.

## Testes

```powershell
.\mvnw.cmd test
```

Os testes unitários verificam os exemplos 6 → 7 e 8 → 11, entradas primas, negativos, zero, um, quadrados perfeitos e limites de `int`. A injeção EJB e o endpoint REST exigem a aplicação em execução no Payara.
