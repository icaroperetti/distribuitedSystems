package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Socket on port 8000");
        System.out.println("Waiting client connection...");

        Socket socket = serverSocket.accept();

        System.out.println("Client connected,IP address:"+socket.getInetAddress().getHostAddress());
    }

}
