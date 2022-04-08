package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Socket on port 8000");
        System.out.println("Waiting client connection...");

        Socket socket = serverSocket.accept();

        System.out.println("Client connected,IP address:"+ socket.getInetAddress().getHostAddress());

        InputStream inputStream = socket.getInputStream();

        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        List<String> listMessages = (ArrayList<String>) objectInputStream.readObject();
        listMessages.forEach((msg) -> System.out.println(msg.toString()));

        socket.close();
        serverSocket.close();

    }

}
