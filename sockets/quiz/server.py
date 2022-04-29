import json
import random
import socket


IP = socket.gethostbyname(socket.gethostname())

# input_port = int(input("Choose the PORT for the server: "))

PORT = 8000

ADDR = (IP, PORT)


BUFFER_SIZE = 1024

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


server.bind(ADDR)  # Binding the server to the address
server.listen()

print(f"Listening on {IP}:{PORT}")


# Get random question from a json file

def get_question(file):
    question_list = []
    answer_list = []
    correct = ""
    questions = json.load(open(file, 'r', encoding='utf8'))

    question = random.choice(list(questions.keys()))

    question_list.append(question)
    answer_list.append(questions[question]['answers'])
    correct = questions[question]['correct_answer']

    return question, answer_list, correct


def quiz():
    question, answer_list, correct = get_question('questions.json')

    # Send the question to server

    server.send(json.dumps(question).encode("utf-8"))

    server.send(json.dumps(answer_list).encode("utf-8"))

    answer = server.recv(BUFFER_SIZE).decode("utf-8")

    if int(answer) == correct:
        server.send("Correct!".encode("utf-8"))
    else:
        server.send("Wrong!".encode("utf-8"))


server, address = server.accept()
quiz()
