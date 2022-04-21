import socket

import os


IP = socket.gethostbyname(socket.gethostname())
PORT = 8000

ADDR = (IP, PORT)


BUFFER_SIZE = 4000000  # 4Mb
SEPARATOR = "_"

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind(ADDR)
server.listen()
print(f"Listening on {IP}:{PORT}")


client_socket, address = server.accept()

print(f"{address} is connected.")

received = client_socket.recv(BUFFER_SIZE).decode()
print("Received", received)

filename, filesize = received.split(SEPARATOR)
print("File name:", filename)

path = "received-files"

if not os.path.exists(path):
    os.makedirs(path)

filename = os.path.join(path, os.path.basename(f"receivedfile_{filename}"))


with open(filename, "wb") as f:
    while True:
        data = client_socket.recv(BUFFER_SIZE)

        if not data:
            break
        f.write(data)

filesize = int(filesize)
server.close()
