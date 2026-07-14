# Atividade #2 - Consumo API + persistência em arquivo

## Introdução
  Nesta atividade iremos praticar todos os conceitos vistos até o momento, iremos construir um "cotador de moedas" onde dado um conjunto de moedas definidos em um arquivo de entrada, elas devem ser consultadas em uma API e posteriormente o resultado gravado em um arquivo CSV.

## Regras
  1. Não mude/toque/mexa/melhore em nada que não esteja nas áreas do código com um TODO marcando, alterar assinatura de métodos, nomes de classes e/ou qualquer outra coisa da estrutura do projeto é proibido
  2. É proibido o uso de bibliotecas externas, apenas utilize aquilo presente no JDK
  3. Interpretação do código é parte da atividade, utilize o debug para entender o que está acontecendo
  4. Implemente apenas o que se pede nos requisitos, fazer a mais não irá te beneficiar
  5. Nomes de variáveis, estrutura do código e qualidade da implementação são avaliados, atente-se aos warnings que a sua IDE irá lhe mostrar
  6. Cópias serão zeradas, fez sua atividade? Não compartilhe ou poderá ser prejudicado
  7. É obrigatório utilizar o projeto modelo, atividades que nao o usem nao serão avaliadas

## Sobre o uso de ferramentas de IA
  A IA sabe fazer, mas e você? Esta é uma atividade simples, se você não consegue implementá-la sem o uso de IA é um forte indicador de que precisa fortalecer os conceitos básicos do desenvolvimento com a linguagem Java. Desligue seu code companion ao fazer esta atividade e vamos voltar as raízes da programação!

## Requisitos
  1. Implemente todos os TODO's espalhados pelo código
  2. Todos os testes devem passar (green)
  3. Na classe CotadorDeMoedas:
    1. Implemente todos os métodos públicos chamados pelo Main
    2. Trate o cenário de caso o arquivo de saída não existir
    3. Ao ler o arquivo de moedas, valide se as moedas são apenas 3 letras, maiúsculas. Caso não atenda a regra, descarte
    4. No momento de cotar, lembre-se de fazer em paralelo usando o CompletableFuture.allOf
    5. Se a lista de moedas é vazia, não fazemos nada no método cotarERegistrar
    6. Caso a consulta de uma moeda falhe, aquelas que tiveram êxito precisam estar no arquivo final
    7. Trate os erros, caso uma exceção ocorra, realize o tratamento dentro do método, não altere as assinaturas dos métodos. Use System.exit(1) para cenários não recuperáveis e coloque mensagens de erro explicativas
    8. Utilize somente recursos de NIO.2
  4. Não altere nada na classe JsonParser
  5. Na classe ClienteCambio
    1. Implemente a request que será feita ao webservice
    2. Caso de erros, retorne um Optional vazio
    3. Caso de sucesso, retorne um Optional com o objeto da cotação
    4. Em caso de erro, mostre uma mensagem informando qual moeda falhou

Sua nota será composta por:
  1. Cada teste automatizado do projeto vale 10 pontos, somando 70 pontos caso acerte todos
  2. Cumprir todos os requisitos exatamente como se pede, 15 pontos
  3. Qualidade do código e correta utilização da linguagem Java, 15 pontos
  Importante: a interpretação e entendimento do código faz parte da solução, o uso da IDE ajuda neste processo

## Entrega
Envie os arquivos CotadorDeMoedas.java e ClienteCambio.java diretamente no moodle.

Relembrando, se você não fizer exatamente da forma como solicitado acima, pode perder nota conforme descrito nas regras e avaliação.

## Avaliação
  1. Quebrar a regra número 1, desconto de 2 pontos por alteração indevida
  2. Quebrar a regra número 2, desconto de 20 pontos por biblioteca utilizada
  3. Não seguir a regra número 5, desconto de 2 pontos por falta de zelo com o 4. código e/ou warnings de sintaxe/qualidade não atendidos na IDE
  5. Não enviar o projeto no formato solicitado na entrega, desconto de 10 pontos
  6. O envio com atraso é permitido, porém, conforme contrato pedagógico, há desconto de 1 ponto para cada dia de atraso após o prazo original de entrega
  7. Atividades em modo rascunho não serão avaliadas
  Caso seu código não compile por qualquer que seja o erro, sua nota será zerada