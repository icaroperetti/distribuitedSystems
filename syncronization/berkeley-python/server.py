import numpy as np
from socket import socket, AF_INET, SOCK_STREAM
from utils import *
from time import sleep


ADDR = ('localhost', 9999)


class Server:
    def __init__(self, num_clients):
        self.num_clients = num_clients
        self.clients = []
        self.server = None
        self.sync_time()

    # Sync the time, getting the current time
    def sync_time(self):
        self.time = get_current_time()

    # Start the server
    def start(self):
        self.server = socket(AF_INET, SOCK_STREAM)
        self.server.bind(ADDR)
        self.server.listen()

        for i in range(self.num_clients):
            print(f'Waiting {i + 1} client...')
            conn, addr = self.server.accept()
            print(f'Client {addr} connected.')
            self.clients.append((conn, addr))
        self.listen()

    # Close the server
    def close(self):
        for client in self.clients:
            conn, addr = client
            conn.close()

        self.server.close()

    # Get the clients time
    def get_times(self):
        request = 'get_time'
        times = []

        # Receive the times and append them to the list
        for client in self.clients:
            conn, addr = client
            conn.send(request.encode())
            response = conn.recv(1024).decode()
            times.append(response)  # Append the time to the list

        return times

    # Update the clients time
    def set_times(self, time):
        request = 'set_time'

        for client in self.clients:
            conn, addr = client
            conn.send(request.encode())
            conn.recv(1024)
            conn.send(time.encode())

    # Calculate the new time
    def calculate_time(self, times):
        hour, minute, second = self.time
        server_time = (int(hour) * 3600) + (int(minute) * 60) + int(second)
        times_in_seconds = []
        used_times = []

        print(f'Server time in calculate: {server_time}')

        for time in times:
            seconds = get_time_in_seconds(time)
            times_in_seconds.append(seconds)

        for time in times_in_seconds:
            # Tolerance of 5 minutes(300 seconds)
            if(time <= server_time + 300 and time >= server_time - 300):
                # Append the time to the list of valid times
                used_times.append(time)

        # Calculate the new time
        # Sum of all the valid times + the server time / the number of valid times + 1 <- server time counts too
        new_time = (np.sum(used_times) + server_time) / (len(times) + 1)

        return seconds_to_time_string(new_time)

    # Function to listen to the clients
    def listen(self):
        while(True):
            print(f'Server: {self.time}')
            times = self.get_times()  # Get the clients time

            print(f'Clients: {times}')
            new_time = self.calculate_time(times)  # Calculates the new time

            self.set_times(new_time)  # Update the clients time
            sleep(0.9)
            self.sync_time()


def main():

    server = Server(num_clients=2)
    try:
        server.start()
    except KeyboardInterrupt:
        server.close()
    finally:
        server.close()

    server.close()


if __name__ == '__main__':
    main()
