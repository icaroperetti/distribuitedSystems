package socket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port = 8000;
        String host = "127.0.0.1";
        Socket socket = new Socket(host, port);
        Scanner in = new Scanner(System.in);



        ArrayList<String> messages = new ArrayList<String>();

        for(;;){
            System.out.println("Selecione a opção\n"
                    + "1 - Adicionar\n"
                    + "2 - Encerrar\n");
            int opt = Integer.parseInt(in.nextLine());
            if (opt == 1) {
                System.out.println("Entre com uma string");
                String message = in.nextLine();
                messages.add(message);

            }

            if(opt == 2 ){
                OutputStream output = socket.getOutputStream();

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(output);

                objectOutputStream.writeObject(messages);
                socket.close();
                break;
            }
        }


    }
}
