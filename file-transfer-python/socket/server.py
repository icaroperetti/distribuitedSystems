import socket
import tqdm
import os


IP = socket.gethostbyname(socket.gethostname())

input_port = int(input("Choose the PORT for the server: "))

PORT = input_port

ADDR = (IP, PORT)


BUFFER_SIZE = 1024

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


server.bind(ADDR)  # Binding the server to the address
server.listen()

print(f"Listening on {IP}:{PORT}")


# Function to check if the directory entered by the user exists
# If it does not exist, ask the user to enter the directory path again
def ask_for_directory():
    directory = str(input("Enter the directory where the files are!: "))
    if os.path.isdir(directory):
        print("Directory found")
        return directory
    else:
        print("The directory you entered does not exist")
        ask_for_directory()


# If the folder is in the project, just it's name is needed

path = ask_for_directory()  # Get the directory

server, address = server.accept()

print(f"{address} is connected.")


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
        if not data:
            break
        progress_bar.update(len(data))
        server.sendall(data)


server.close()
