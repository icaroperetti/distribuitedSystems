import json
import random
import socket
import io
import time


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


def get_question(numOfQ, file):
    question_list = []
    answer_list = []
    correct_list = []

    questions = json.load(io.open(file, 'r', encoding='utf8'))

    for i in range(numOfQ):
        question = random.choice(list(questions.keys()))
        question_list.append(question)
        answer_list.append(questions[question]['answers'])
        correct_list.append(questions[question]['correct_answer'])

    return question, answer_list, correct_list, question_list


def getUserAndPassword(file):
    users = []
    name = ''
    password = ''
    with io.open(file, "r", encoding="utf8") as f:
        for line in f.readlines():
            aux = line.strip('\n').split(":")
            name = aux[0]
            password = aux[1]
            users.append({
                'name': name,
                'password': password
            })
    return users


USERS = getUserAndPassword('users.txt')


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

    question, answer_list, correct_list, question_list = get_question(
        3, 'questions.json')

    corrects = 0

    numOfQ = len(question_list)
    server.send(str(numOfQ).encode())
    time.sleep(7)

    for i in range(numOfQ):
        time.sleep(1.5)

        # Check repeated questions
        if question_list[i] in question_list[:i]:
            print("Question repeated.")
            question_list.choose(question_list[i])

        # Send question
        server.send(str(question_list[i]+"\n"+str(answer_list[i])).encode())
        time.sleep(3)

        # Get answer
        answer = int(server.recv(BUFFER_SIZE).decode())

        # Check answer
        if answer == correct_list[i]:
            corrects += 1
            server.send("Correta!".encode())
        else:
            server.send("Incorreta!".encode())

    # Send result
    result = f"Você acertou {corrects} de {numOfQ}"
    server.send(result.encode())
    print("Quiz finished.")
    server.close()


server, address = server.accept()

login()
quiz()
