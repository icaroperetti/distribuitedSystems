package socket.question1;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        int port = 8000;
        String host = "127.0.0.1";
        Socket socket = new Socket(host, port);
        Scanner in = new Scanner(System.in);

        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        while (true) {
            String message = inputStream.readUTF();
            System.out.println(message);

            if (message.equals("Conexão encerrada!")) {
                break;
            }

            outputStream.writeUTF(in.nextLine());
            outputStream.flush();
        }
    }
}
