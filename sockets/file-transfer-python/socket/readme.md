# Transferência de Arquivos via socket

- Python version: 3.9.7
- Ruby version: 3.1.1p18
- Testado com arquivos de até 7GB e funcionou com sucesso.

Para executar basta adicionar o arquivo que deseja trasnferir na pasta <strong>files</strong> do projeto.

- Executar os comandos abaixo

Instalação de dependências:

```
   pip install -r requirements.txt
```

Execução do servidor:

```
   python server.py
```

Execução do cliente ruby:

```
  ruby client.rb
```

Execução cliente python:

```
    python client.py
```

Os arquivos recebidos ficarão na pasta <strong>received-files</strong> do projeto.