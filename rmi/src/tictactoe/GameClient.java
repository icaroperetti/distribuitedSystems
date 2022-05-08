package tictactoe;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class GameClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",12345);
            TicTacToeInterface ticTacToe = (TicTacToeInterface) registry.lookup("TicTacToe");
            Scanner in = new Scanner(System.in);
            System.out.println("Enter your name: ");
            String name = in.nextLine();
            Random r = new Random();
//            int id = r.nextInt(10)+1;

            System.out.print("Forneca o id do seu usuario: ");
            int id = Integer.parseInt(in.nextLine());

            Player player = new Player(name, id,false);

            ReturnMessage msg = ticTacToe.enter(player);
            System.out.println(msg.getMessage());
            int opt = 0;
            int row = -1;
            int col = -1;
            boolean stopLoop = false;
            boolean validPlay;

            if(msg.getCode() == 1){
                do{
                    System.out.println("1 - Play\n2 - Exit");
                    opt = Integer.parseInt(in.nextLine());
                    if(opt == 1){
                        ticTacToe.getNumOfPlayers();

                        while(!ticTacToe.gameCanBePlayed()){
                            if(!stopLoop) {
                                System.out.println("Waiting for other player to start the game");
                                stopLoop = true;
                            }
                        }
                        System.out.println("Game started");
                        stopLoop = false;

                        while(!ticTacToe.isGameOver()){
                            if(ticTacToe.getPlayerTurn(id)){
                                do {
                                    System.out.println("Your turn," +player.getName());
                                    System.out.println(ticTacToe.getBoard());
                                    System.out.println("Enter row: ");
                                    row = Integer.parseInt(in.nextLine());
                                    System.out.println("Enter col: ");
                                    col = Integer.parseInt(in.nextLine());
                                    System.out.println("");

                                    validPlay = ticTacToe.isValidMove(row, col);
                                    if (!validPlay) {
                                        System.out.println("Invalid move!");
                                    }else {
                                        ticTacToe.play(player,row, col);
                                        ticTacToe.switchPlayer();
                                        if (ticTacToe.isGameOver()) {
                                            System.out.println(ticTacToe.getBoard());
                                            System.out.println("You won!");
                                            ticTacToe.exit();
                                            return;
                                        }
                                        stopLoop = false;
                                    }
                                }while(!validPlay);
                            }else{
                                if(!stopLoop){
                                    System.out.println("\n"+ ticTacToe.getBoard());
                                    System.out.println("Waiting for other player to play");
                                    stopLoop = true;
                                }
                            }
                        }
                        System.out.println(ticTacToe.getBoard());
                        System.out.println("Game ended, you lost!");
                        ticTacToe.exit();

                        return;
                    }
                }while (opt != 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}