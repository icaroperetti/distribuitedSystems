import socket
import tqdm
import os


IP = socket.gethostbyname(socket.gethostname())
PORT = 8000

ADDR = (IP, PORT)


BUFFER_SIZE = 1024

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

server.bind(ADDR)  # Binding the server to the address
server.listen(3)  # Limit of connections

print(f"Listening on {IP}:{PORT}")


server, address = server.accept()

print(f"{address} is connected.")


path = r"files"  # Folder name

# Get the file name from the client
filename = server.recv(BUFFER_SIZE).decode()

address_file = os.path.join(path, os.path.basename(
    f"{filename}"))  # Get the file inside the folder

filesize = os.path.getsize(address_file)  # Getting de file size

# Progress bar using the lib tqdm
progress_bar = tqdm.tqdm(range(
    filesize), f"Sending {filename}", unit="B", unit_scale=True)

with open(address_file, "rb") as f:
    for data in f.readlines():
        progress_bar.update(len(data))
        server.sendall(data)
    print("\nFile sent!")

server.close()
