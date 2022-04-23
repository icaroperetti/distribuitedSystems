import socket
import os

IP = socket.gethostbyname(socket.gethostname())

PORT = 8000
ADDR = (IP, PORT)

SEPARATOR = "_"
BUFFER_SIZE = 4000000  # 4Mb


client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    client.connect(ADDR)
    print("Connected to server.")
except:
    print("Connection failed!\nCheck if the server is running.")
    exit()


file = input("Enter the name of the file you want to receive: ")

client.send(file.encode())

path = "received-files"

if not os.path.exists(path):
    os.makedirs(path)

file_receive = os.path.join(path, os.path.basename(file))

with open(file_receive, "wb") as f:
    while True:
        data = client.recv(BUFFER_SIZE)
        if not data:
            break
        f.write(data)

    print("File received!")


client.close()
