# Introdução

Nesta atividade iremos utilizar os conhecimentos sobre **concorrência estruturada** para resolver um problema que pode acontecer na vida real: **consulta de estoque e criação de pedidos**.

Nesta atividade você está desenvolvendo um sistema que, de forma contínua, irá receber diferentes arquivos em um determinado formato com itens para compor um pedido de venda. Seu objetivo principal é utilizar **concorrência estruturada** para fazer isso no menor tempo possível e da forma mais eficiente possível.

---

# Regras

- Não mude/toque/mexa/melhore em nada que não esteja nas áreas do código com um `TODO` marcando. Alterar assinatura de métodos, nomes de classes e/ou qualquer outra coisa da estrutura do projeto é proibido.
- É proibido o uso de bibliotecas externas. Utilize apenas aquilo presente no JDK.
- A interpretação do código é parte da atividade. Utilize o debug para entender o que está acontecendo.
- Implemente apenas o que se pede nos requisitos. Fazer a mais não irá te beneficiar.
- Nomes de variáveis, estrutura do código e qualidade da implementação são avaliados. Atente-se aos warnings que a sua IDE irá lhe mostrar.
- Cópias serão zeradas. Fez sua atividade? Não compartilhe ou poderá ser prejudicado.

# Requisitos

## Classe `ProcessadorDePedidos`

### Método `processarArquivo`

- Utilize a classe `LeitorDePedidos` para ler o arquivo.
- Utilize concorrência estruturada para processar cada um dos pedidos.
- Após o processamento de todos, separe aqueles que foram aprovados ou rejeitados (**dica:** use *pattern matching*).
- Ao fim, retorne a classe `Relatorio` com os pedidos processados.

### Método `processarPedido`

- Utilize concorrência estruturada para executar as buscas de estoque e preço de maneira paralela.
- Caso uma das buscas falhe, o processo deve ser abortado e um erro informativo retornado utilizando a classe `PedidoRejeitado`.
- Caso o estoque seja menor que a quantidade solicitada, use `PedidoRejeitado` para indicar o problema.
- Cote o frete usando o método `cotarFrete`.
- Em posse do valor do frete, calcule o valor total do pedido:

```text
(valor unitário × quantidade) + frete
```

- Caso tudo dê certo, retorne `PedidoAprovado` com os devidos atributos preenchidos.

### Método `cotarFrete`

- Utilize concorrência estruturada.
- Faça a busca de fretes invocando em paralelo os métodos:
    - `cotarFreteTransportadoraUm`
    - `cotarFreteTransportadoraDois`
- Ambos pertencem à classe `ServicosExternos`.
- Nesse caso, queremos aquele que responder mais rápido, logo não precisamos esperar ambas responderem.