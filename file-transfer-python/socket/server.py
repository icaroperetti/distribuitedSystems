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


server, address = server.accept()

print(f"{address} is connected.")


path = "files"

filename = server.recv(BUFFER_SIZE).decode()

address_file = os.path.join(path, os.path.basename(f"{filename}"))

print(address_file)


with open(address_file, "rb") as f:
    for data in f.readlines():
        server.send(data)
    print("File sent!")

server.close()
