# Transferência de Arquivos via socket

- Python version: 3.9.7
- Node.JS version: 14.18.0

Para executar basta adicionar o arquivo que deseja trasnferir na pasta <strong>files</strong> do projeto.

- Executar os comandos abaixo

Instalação de dependências:

```
   pip install -r requirements.txt
```

Execução do cliente e servidor:

```
   python server.py

   python client.py
```

Execução do cliente node:

```
  node node-client.js
```

- Quando utilizando o cliente em node.js, os arquivos recebidos ficarão na raiz do projeto.

- Quando utilizado o cliente em python, os arquivos recebidos ficarão na pasta <strong>received-files</strong> do projeto.
