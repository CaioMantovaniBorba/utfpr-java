Analise o projeto Java atual e implemente a atividade seguindo **rigorosamente** as instruções abaixo.

## Objetivo

O projeto trabalha com um relatório CSV contendo vendas realizadas por vendedores em diferentes regiões do Brasil.

Implemente os métodos solicitados utilizando os recursos de programação funcional do Java, principalmente:

- Streams
- Collectors
- Gatherers

## RESTRIÇÕES IMPORTANTES

### 1. Altere SOMENTE código marcado com TODO

Você pode modificar exclusivamente as áreas do código que possuem comentários `TODO`.

NÃO altere:

- Assinaturas de métodos
- Nomes de métodos
- Nomes de classes
- Construtores
- Atributos existentes
- Estrutura do projeto
- Outras classes
- Código que não esteja explicitamente marcado com `TODO`

Não faça refatorações ou melhorias fora dos `TODO`, mesmo que identifique oportunidades de melhoria.

### 2. Classes permitidas

Implemente somente os `TODO` existentes nas classes:

- `LeitorDeVendas`
- `LocalDateConverter`

É proibido alterar qualquer outra classe.

### 3. Bibliotecas

Não adicione nenhuma biblioteca externa.

Utilize exclusivamente APIs e recursos disponíveis no JDK configurado no projeto.

Não altere arquivos de dependências ou configuração do projeto para adicionar bibliotecas.

### 4. Programação funcional

Com exceção do método:

`topNVendasPorValor`

todos os demais métodos devem obrigatoriamente utilizar operações funcionais.

Utilize, conforme apropriado:

- `Stream`
- `map`
- `filter`
- `sorted`
- `reduce`
- `collect`
- `Collectors`
- `Gatherers`
- `groupingBy`
- `mapping`
- `counting`
- `summingInt`
- `summingDouble`
- `maxBy`
- `minBy`
- outras operações funcionais disponíveis no JDK

Não utilize `for`, `while` ou outros loops imperativos nos métodos em que operações funcionais são obrigatórias.

### 5. Implementação

Implemente EXATAMENTE o comportamento solicitado pelos `TODO` e pelo código existente.

Antes de implementar:

1. Analise as classes do projeto.
2. Analise os modelos utilizados.
3. Analise como o CSV é carregado e convertido.
4. Analise os testes existentes.
5. Analise principalmente `LeitorDeVendasTest`.
6. Identifique o comportamento esperado de cada método.
7. Somente depois implemente os `TODO`.

Não invente requisitos que não estejam presentes no código, nos testes ou nas instruções.

### 6. Qualidade

A implementação deve:

- Compilar sem erros.
- Evitar warnings desnecessários.
- Utilizar nomes de variáveis claros.
- Manter o estilo existente no projeto.
- Evitar código desnecessário.
- Utilizar corretamente os recursos funcionais do Java.
- Não adicionar comentários desnecessários.
- Não fazer alterações cosméticas fora dos `TODO`.

## Testes

A classe `LeitorDeVendasTest` possui 10 testes.

Após implementar os métodos:

1. Compile o projeto.
2. Execute todos os testes existentes.
3. Verifique especificamente os 10 testes de `LeitorDeVendasTest`.
4. Caso algum teste falhe, investigue a causa.
5. Corrija somente a implementação dentro das áreas permitidas pelos `TODO`.
6. Execute novamente os testes.
7. Repita até que todos os testes possíveis estejam passando.

Não altere os testes para fazê-los passar.

## ATENÇÃO

Não resolva um problema alterando código fora dos `TODO`.

Se encontrar um problema que aparentemente exija uma alteração fora de um `TODO`, NÃO faça a alteração.

Nesse caso, apenas informe o problema ao final.

Também não:

- Crie novas classes sem necessidade.
- Modifique testes.
- Modifique arquivos de configuração.
- Adicione dependências.
- Altere a estrutura do projeto.
- Faça refatorações não solicitadas.

## Resultado esperado

Ao finalizar:

1. Todos os `TODO` de `LeitorDeVendas` devem estar implementados.
2. Todos os `TODO` de `LocalDateConverter` devem estar implementados.
3. Nenhuma outra classe deve ter sido modificada.
4. Nenhum código fora das regiões permitidas deve ter sido alterado.
5. Os métodos, exceto `topNVendasPorValor`, devem utilizar programação funcional.
6. O projeto deve compilar.
7. Os testes devem passar.

Ao terminar, apresente um resumo curto contendo:

- Quais métodos foram implementados.
- Quais recursos funcionais foram utilizados em cada método.
- Resultado da compilação.
- Resultado dos testes.
- Confirmação de que nenhuma classe além de `LeitorDeVendas` e `LocalDateConverter` foi alterada.
- Confirmação de que nenhuma biblioteca externa foi adicionada.

Antes de finalizar, execute `git diff` (ou mecanismo equivalente disponível no ambiente) e confira se todas as alterações respeitam as restrições acima.