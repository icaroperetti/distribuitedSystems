import socket
import os

IP = str(input("Enter IP: "))

PORT = int(input("Enter PORT: "))
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

# Adapting to save to a path
file_received = os.path.join(path, os.path.basename(f"received_file{file}"))

with open(file_received, "wb") as f:
    while True:
        data = client.recv(BUFFER_SIZE)
        if not data:
            break
        f.write(data)

    print("File received!")


client.close()
