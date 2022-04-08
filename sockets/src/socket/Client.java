package socket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port = 8000;
        String host = "127.0.0.1";
        Socket socket = new Socket(host, port);




        ArrayList<String> messages = new ArrayList<String>();

        messages.add("Icaro");
        messages.add("Rafaels");
        messages.add("Eduardo, velotax não tem como esquece");


        OutputStream output = socket.getOutputStream();

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);

        objectOutputStream.writeObject(messages);

        socket.close();
    }
}
