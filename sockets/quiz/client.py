import socket
import os
import time
import platform

IP = socket.gethostbyname(socket.gethostname())
PORT = 8000

BUFFER_SIZE = 1024

ADDR = (IP, PORT)

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    client.connect(ADDR)
    print("Connected to server.")
except:
    print("Connection failed!\nCheck if the server is running.")
    exit()


def login():
    name = str(input("Digite o seu nome de usuario: "))
    password = str(input("Digite a senha: "))
    client.send(name.encode())
    client.send(password.encode())

    res = client.recv(BUFFER_SIZE).decode().split(":")[1]
    authorized = eval(res)

    if authorized:
        print("Autenticado.")
        return
    else:
        print("Tente novamente")
        login()


def quiz():
    numOfQ = int(client.recv(BUFFER_SIZE).decode())
    print("Numero de questoes:", numOfQ)

    for i in range(int(numOfQ)):
        question = client.recv(BUFFER_SIZE).decode()
        print(question)

        answer = int(input("Digite a sua resposta: "))
        client.send(str(answer).encode())

        res = client.recv(BUFFER_SIZE).decode()
        print("\n", res)
        time.sleep(1.5)

        if platform.system() == "Windows":
            os.system("cls")
        elif platform.system() == "Linux":
            os.system("clear")

    result = client.recv(BUFFER_SIZE).decode()
    print("\n", result)


login()
quiz()
