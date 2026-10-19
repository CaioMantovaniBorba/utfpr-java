# Atividade 04 — servidor com threads

Autor: Caio Mantovani Borba.

Continuação da atividade 03, com três classes no pacote `local.redes`: `Pessoa`, `Servidor` e `FormCliente`. Requer JDK 8 ou superior e ambiente gráfico para o cliente Swing. Não utiliza bibliotecas externas nem arquivos `.form`.

## Compilar

Em um terminal na pasta `atividade04`:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 -d out Pessoa.java Servidor.java FormCliente.java
```

## Executar

Primeiro, inicie o servidor em modo console:

```powershell
java -cp out local.redes.Servidor
```

Em outro terminal, na pasta `atividade04`, execute o cliente:

```powershell
java -cp out local.redes.FormCliente
```

Preencha **Nome** e **Idade** e clique em **Enviar**. O objeto `Pessoa` é serializado e transmitido para `127.0.0.1:50000`. O servidor imprime nome e idade no console e envia `Dados recebidos corretamente!`. O cliente exibe essa resposta na área **Retorno do Servidor**.

Execute o cliente novamente, inclusive em várias janelas, ou envie novos dados pela mesma janela: o servidor continua ativo e não precisa ser reiniciado. O cliente nunca inicia o servidor. Para encerrar o servidor manualmente, use **Ctrl+C** no terminal onde ele está executando.

## Threads e conexões

`Servidor` implementa `Runnable`. O laço principal aguarda conexões em `accept()` e inicia uma `Thread` para cada cliente. Cada thread recebe seu próprio socket; um cliente aguardando dados não bloqueia o atendimento dos demais.

O `ServerSocket` permanece aberto entre os atendimentos, mantendo o serviço em espera por novos clientes. Apenas o socket individual de uma transmissão concluída é fechado para liberar seus recursos. Uma falha de leitura afeta somente aquele atendimento. O limite de leitura é de 10 segundos por cliente; ele não encerra o servidor.

Não há implementação de threads de comunicação no cliente: o envio é síncrono. `EventQueue.invokeLater` utiliza apenas a fila padrão de eventos do Swing para criar a janela. A interface aguarda a resposta durante o envio, com limites de 5 segundos para conexão e 10 segundos para leitura.

## Entrega

Anexe **Pessoa.java**, **Servidor.java** e **FormCliente.java** individualmente, **sem compactar**. Todos contêm o comentário de autoria. Não é necessário enviar `.form`, `out` ou arquivos temporários. Finalize a submissão no ambiente de ensino em **Enviar Tarefa por Definitivo**.
