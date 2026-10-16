# Atividade 01 — Java Redes

Aplicação de console com sockets TCP, sem dependências externas. Requer JDK 8 ou superior.

## Compilar

Na pasta `Redes`, execute:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 -d out Servidor.java Cliente.java
```

## Executar

Em um terminal, inicie o servidor:

```powershell
java -cp out local.redes.Servidor
```

Em outro terminal, na mesma pasta, inicie o cliente:

```powershell
java -cp out local.redes.Cliente
```

Digite o CPF quando solicitado. A entrada vem do teclado; o servidor realiza toda a validação e devolve o resultado, exibido nos dois terminais. A comunicação utiliza `127.0.0.1`, porta `50000`, com `writeUTF`/`readUTF`.

O servidor atende uma consulta e fecha tanto a conexão quanto a porta. Para consultar outro CPF, execute novamente o servidor e o cliente.

## Validação

- Aceita 11 dígitos ou a máscara `xxx.xxx.xxx-xx`, preservando zeros iniciais.
- Confere os dois dígitos verificadores com pesos de 10 a 2 e de 11 a 2, respectivamente, usando o resto da divisão por 11.
- Rejeita `000.000.000-00`, tamanho incorreto e caracteres/formatação inválidos.
- Conforme a regra específica da página 6 do enunciado, outras sequências repetidas podem ser aceitas se os cálculos conferirem: `222.222.222-22` é aceito neste exercício.

## Entrega

Anexe `Servidor.java` e `Cliente.java` individualmente no ambiente de ensino, sem ZIP/RAR. Ambos declaram `package local.redes;` e possuem o comentário de autoria `Caio Mantovani Borba`. Finalize a submissão no botão “Enviar Tarefa por Definitivo”.

O PDF original, esta documentação e a pasta de compilação `out` não são necessários para a entrega dos fontes.
