# Atividade 03 — envio de Pessoa por sockets

Autor: Caio Mantovani Borba.

Três arquivos Java, no pacote `local.redes`, sem bibliotecas externas ou arquivo `.form`. Requer JDK 8 ou superior e ambiente gráfico para abrir o cliente Swing.

## Compilar

Abra um terminal na pasta `atividade03`:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 -d out Pessoa.java Servidor.java FormCliente.java
```

## Executar

Primeiro, inicie o servidor no terminal:

```powershell
java -cp out local.redes.Servidor
```

Em outro terminal, também na pasta `atividade03`, abra o cliente:

```powershell
java -cp out local.redes.FormCliente
```

Preencha **Nome** e **Idade** e clique em **Enviar**. O cliente transmite um objeto `Pessoa` serializado para `127.0.0.1:50000`. O servidor exibe nome e idade no console e retorna `Dados recebidos corretamente!`, que aparece na área **Retorno do Servidor**.

O servidor encerra após uma transmissão, fechando a conexão e a porta. Para enviar novamente, reinicie o servidor. O cliente nunca inicia o servidor.

O envio é síncrono, sem implementação de threads, executores ou `SwingWorker`. `EventQueue.invokeLater` apenas agenda a criação da janela na fila de eventos padrão do Swing. Durante a comunicação, a interface aguarda o resultado; há limites de 5 segundos para conexão e 10 segundos para leitura.

## Entrega

Anexe individualmente `Pessoa.java`, `Servidor.java` e `FormCliente.java`, **sem compactar**. A interface está inteiramente no código; não é necessário `.form`. Não envie a pasta `out` ou os arquivos temporários. No ambiente de ensino, finalize em **Enviar Tarefa por Definitivo**.
