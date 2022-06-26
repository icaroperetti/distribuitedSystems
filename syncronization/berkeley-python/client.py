import numpy as np
from socket import socket, AF_INET, SOCK_STREAM
from utils import *


ADDR = ('localhost', 9999)


class Client:
    def __init__(self):
        self.client = None
        h, m, s = get_current_time()
        self.set_time(h, m, s)

    # Connect to the server
    def connect(self):
        self.client = socket(AF_INET, SOCK_STREAM)
        self.client.connect(ADDR)

    # Close the connection
    def disconnect(self):
        self.client.close()

    # Get the client time
    def get_time(self):
        hour, minute, second = self.time
        return f'{hour}:{minute}:{second}'

    # Update client time
    def set_time(self, hour, minute, second):
        self.time = (hour, minute, second)

    def do_sync(self):
        while True:
            # Get the server response
            response = self.client.recv(1024).decode()

            if(response == 'get_time'):
                # Send the client time
                self.client.send(self.get_time().encode())

            elif(response == 'set_time'):
                self.client.send('ready'.encode())

                # Get the new time from the server
                new_time = self.client.recv(1024).decode()
                hour, minute, second = new_time.split(':')  # Split the time

                # Get the client time in seconds
                old_time = get_time_in_seconds(self.get_time())
                # Get the updated time in seconds
                curr_time = get_time_in_seconds(new_time)

                self.set_time(hour, minute, second)  # Update de client time
                # Calculates the difference between the old and new time
                diff = np.abs(curr_time - old_time)

                # print("Difference: {}".format(diff))

                if diff - 1 == 0:  # Gambiarra because of the sleep() needed in socket communication
                    print(
                        f" print(f'Current time: {format_time((hour, minute, second))}')(SYNC!)")
                else:
                    print(
                        f" print(f'Current time: {format_time((hour, minute, second))}')(NOT SYNC!)")

                self.client.send(''.encode())


def main():
    client = Client()
    try:
        client.connect()
        client.do_sync()
    finally:
        client.disconnect()


if __name__ == '__main__':
    main()
