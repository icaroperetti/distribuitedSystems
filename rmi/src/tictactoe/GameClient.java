package tictactoe;

import tictactoe.ReturnMessage;
import tictactoe.TicTacToeInterface;
import tictactoe.Player;
import tictactoe.ReturnMessage;
import tictactoe.TicTacToeInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",12345);
            TicTacToeInterface ticTacToe = (TicTacToeInterface) registry.lookup("TicTacToe");
            Scanner in = new Scanner(System.in);
            System.out.println("Enter your name: ");
            String name = in.nextLine();
            int row = -1;
            int col = -1;
            int opt = -1;
            int id = 0;
            boolean validPlay = false;

            Random rand = new Random();
            int maxNumber = 130;
            int randomNumber = rand.nextInt(maxNumber) + 1;
            Date date = new Date();

            while(id == 0) {
                id = (int) (date.getTime() % (randomNumber * 2 + 3));
            }


            System.out.println("Your id is: " + id);

            Player player = new Player(name, id);

           ReturnMessage msg = ticTacToe.enter(player);
           System.out.println(msg.getMessage());

              do{
                  if(ticTacToe.getNumOfPlayers() == 1){
                      System.out.println("Waiting for other player to start the game...");
                      //Mudando o valor do stopLoop para true para parar o loop
                      TimeUnit.SECONDS.sleep(3);
                  }else {
                      System.out.println("Game started");
                  }
                  while (!ticTacToe.checkWin()) {
                      if(ticTacToe.checkTie()){
                          System.out.println("Tie!");
                          ticTacToe.removeAllPlayers();
                          return;
                      }
                      if (ticTacToe.playerCanPlay(player)) {
                          do {
                              System.out.println(ticTacToe.showBoard());
                              System.out.println("Enter row: ");
                              row = in.nextInt();
                              System.out.println("Enter col: ");
                              col = in.nextInt();
                              validPlay = ticTacToe.isValidMove(row, col);
                              if (validPlay) {
                                  ticTacToe.play(player, row, col);
                                  System.out.println(ticTacToe.showBoard());
                                  if (ticTacToe.checkWin()) {
                                      System.out.println("You won! " + player.getName());
                                      System.out.println(ticTacToe.showBoard());
                                      ticTacToe.removeAllPlayers();
                                      return;
                                  }
                              } else {
                                  System.out.println("Invalid play!");
                              }
                          } while (!validPlay);

                      } else {
                          System.out.println("Waiting for opponent...");
                          TimeUnit.SECONDS.sleep(3);
                      }
                  }
                  if(!ticTacToe.checkTie()){
                      System.out.println("You Lost!");
                      ticTacToe.removeAllPlayers();
                      return;
                  }
              }while (opt != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
