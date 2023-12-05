

# Jogo de Aventura

Este é um aplicativo de servidor e cliente em Java para um jogo de aventura baseado em texto. O servidor guia o cliente através de uma narrativa interativa, permitindo escolhas que afetam o desenrolar da história.


## Obtendo o projeto

Clonar o Repositório:

```bash
  git clone https://github.com/iasmim21/socket-server-client-java.git
```


## Servidor

Na raiz do projeto:

```bash
  cd server/src
```

Compilar e Executar o Servidor:

```bash
  javac Server.java
  java Server
```

Se tudo ocorreu com sucesso, deve ser exibida a mensagem: "Aguardando conexão..."

### Visão Geral do Jogo:
- O servidor aguarda a conexão do cliente na porta 5555.
- O cliente se conecta ao servidor para iniciar a aventura.


### Lógica do Servidor:
- O servidor guia o cliente por meio de etapas (Step) e opções (Option).
- Cada escolha do cliente afeta o desenrolar da história.


### Tratamento de Conclusão:
- O jogo possui etapas concluídas, e alcançá-las encerra a conexão do cliente.
- O servidor lida com desconexões e encerra a conexão quando a história é concluída.


## Cliente

Na raiz do projeto:

```bash
  cd client/src
```

Compilar e Executar o Cliente:

```bash
  javac Client.java
  java Client
```



Se tudo ocorreu com sucesso, deve ser exibida a mensagem: "Digite 'start' para iniciar sua jornada"

## Rodar o projeto com JAR (outra opção)
Na raiz do projeto:
```bash
  cd server/out/artifacts/server_jar
  java -jar server.jar
```

Em outro terminal, ou em outra máquina, na raiz do projeto:
```bash
  cd client/out/artifacts/client_jar
  java -jar client.jar "server_ip" 
  
  //substitua server_ip pelo ip do servidor
  //se não informar, por padrão será localhost
```


### Iniciar jornada:
- Digite 'start' para iniciar a aventura.
- Siga as instruções apresentadas para fazer escolhas e progredir na história.

### Lógica do Cliente:
- O cliente se conecta ao servidor e interage com a narrativa por meio do console.
- O jogador faz escolhas e recebe atualizações sobre o desenrolar da história.

### Tratamento de Escolhas:
- O cliente valida as escolhas do jogador, garantindo que sejam opções válidas.
- Caso uma escolha inválida seja feita, o cliente solicita uma nova entrada.
- Quando a história é concluída, o cliente exibe uma mensagem de encerramento.


# Autores

Este projeto foi desenvolvido como parte da matéria de Redes de Computadores na UNESC. Os autores responsáveis pela criação e implementação são:

- Iasmim Bitencourt
- Yasmin Feltrin
