# Transferência de Arquivos via socket

Para executar basta adicionar o arquivo que deseja trasnferir na raiz do projeto.

Executar os comandos abaixo:

Instalação de dependências:

```
   pip install -r requirements.txt
```

Execução do cliente e servidor:

```
   python server.py

   python client.py
```

Depois de transferido o arquivo irá aparecer da seguinte maneira: <strong>receivedfile_nomedoarquivo.extensão</strong> na pasta received-files

Para transferir arquivos maiores que 4mb a variavel <strong>BUFFER_SIZE</strong> deverá ser alterada.
