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

print(filename)

address_file = os.path.join(path, os.path.basename(
    f"{filename}"))  # Get the file inside the folder

print(address_file)

# If the file not exist, the server sends a message to the client
if not os.path.exists(address_file):
    print("File not found")
    server.send("File not found".encode())
    exit()

# Progress bar using the lib tqdm
filesize = os.path.getsize(address_file)  # Getting de file size

progress_bar = tqdm.tqdm(range(
    filesize), f"Sending {filename}", unit="B", unit_scale=True)  # Creating the progress bar

# Sending the file to client
with open(address_file, "rb") as f:
    for data in f.readlines():
        if not data:
            break
        # Updating the progress bar according to the file size
        progress_bar.update(len(data))
        server.sendall(data)


# Closing the server
server.close()
