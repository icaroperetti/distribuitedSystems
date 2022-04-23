import socket


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

with open(file, "wb") as f:
    while True:
        data = client.recv(BUFFER_SIZE)

        if not data:
            break
        f.write(data)


client.close()
