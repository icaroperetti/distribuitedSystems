package tictactoe;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class GameClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",12345);
            TicTacToeInterface ticTacToe = (TicTacToeInterface) registry.lookup("TicTacToe");
            Scanner in = new Scanner(System.in);
            System.out.println("Enter your name: ");
            String name = in.nextLine();

            //ID pode ser gerado pelo servidor
            System.out.println("Enter your ID: ");
            int id = Integer.parseInt(in.nextLine());

            Player player = new Player(name, id);

           ReturnMessage msg = ticTacToe.enter(player);
           System.out.println(msg.getMessage());

          if(msg.getCode() == 1){
              int opt = -1;
              do{
                  //System.out.println("Enter your move: ");
                  //int move = Integer.parseInt(in.nextLine());

                  System.out.println("1 - Play\n2 - Exit");
                  opt = Integer.parseInt(in.nextLine());
                  if(opt == 1){
                      System.out.println("Enter your move: ");
                  }
              }while (opt != 0);
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
