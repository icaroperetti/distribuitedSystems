import json
import random
import socket
import io
import time


IP = socket.gethostbyname(socket.gethostname())


PORT = 8000

ADDR = (IP, PORT)


BUFFER_SIZE = 1024


server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


server.bind(ADDR)  # Binding the server to the address
server.listen()

print(f"Listening on {IP}:{PORT}")

server, address = server.accept()

# Get random question from a json file


def get_question(numOfQ, file):
    question_list = []
    answer_list = []
    correct_list = []

    questions = json.load(io.open(file, 'r', encoding='utf8'))

    for i in range(numOfQ):
        if numOfQ > len(list(questions.keys())):
            server.send("Não há mais questões!".encode())
            exit()
        question = random.choice(list(questions.keys()))
        # Check if the question is already in the list
        # If it is, get another question
        while question in question_list:
            print("used", question, "\n\n")
            question = random.choice(list(questions.keys()))

        question_list.append(question)
        answer_list.append(questions[question]['answers'])
        correct_list.append(questions[question]['correct_answer'])

    return answer_list, correct_list, question_list


# Get the user and password from de file
def getUserAndPassword(file):
    users = []
    name = ""
    password = ""
    with io.open(file, "r", encoding="utf8") as f:
        for l in f.readlines():
            name, password = l.strip('\n').split(":")
            users.append({
                "name": name,
                "password": password
            })
    return users


USERS = getUserAndPassword('users.txt')

# Make Login


def login():
    def checkLogin(user, password):
        for user in USERS:
            if user['name'] == name and user['password'] == password:
                return True
        return False

    name = server.recv(BUFFER_SIZE).decode()
    password = server.recv(BUFFER_SIZE).decode()

    authorized = checkLogin(name, password)

    message = f"Authorized:{str(authorized)}"
    server.send(message.encode())

    if authorized:
        print(f"client {address} logged.")
        return
    else:
        login()


def quiz():
    # Pega a lista de respostas, lista de respostas corretas e lista de questões
    answer_list, correct_list, question_list = get_question(
        5, 'questions.json')

    corrects = 0

    print("QUESTION LIST: ", question_list, "\n\n")

    numOfQ = len(question_list)

    server.send(str(numOfQ).encode())

    time.sleep(7)

    for i in range(numOfQ):
        question = question_list[i]

        # Send question
        server.send(str(question+"\n"+str(answer_list[i])).encode())

        # Get answer
        answer = int(server.recv(BUFFER_SIZE).decode())

        # Check answer
        if answer == correct_list[i]:
            corrects += 1
            server.send("Correta!".encode())
        else:
            server.send("Incorreta!".encode())
        time.sleep(3.5)

    # Send result
    result = f"Você acertou {corrects} de {numOfQ}"
    server.send(result.encode())
    print("Quiz terminou")
    server.close()


login()
quiz()
