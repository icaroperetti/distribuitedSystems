import socket


IP = socket.gethostbyname(socket.gethostname())
PORT = 8000


ADDR = (IP, PORT)

client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    client.connect(ADDR)
    print("Connected to server.")
except:
    print("Connection failed!\nCheck if the server is running.")
    exit()


question = client.recv(1024).decode()

answer_list = client.recv(1024).decode()


print(question)
print(answer_list)


input_answer = input("Your answer: ")
client.send(input_answer.encode())

server_response = client.recv(1024).decode()

print(server_response)
