# Transferência de arquivos via socket

Para executar basta adicionar o arquivo que deseja trasnferir na raiz do projeto.

Executar os comandos abaixo:

Instalação de dependências:

```
   pip install -r requirements.txt
```

Execução do cliente e servidor:

```
   1 - python server.py

   2 - python client.py
```

Depois de transferido o arquivo irá aparecer da seguinte maneira: <strong>receivedfile_nomedoarquivo.extensão</strong> na pasta <strong>received-files</strong>

Para transferir arquivos maiores que 4mb a variavel <strong>BUFFER_SIZE</strong> deverá ser alterada.
