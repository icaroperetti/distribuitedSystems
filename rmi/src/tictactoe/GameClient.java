package tictactoe;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",12345);
            TicTacToeInterface ticTacToe = (TicTacToeInterface) registry.lookup("TicTacToe");
            Scanner in = new Scanner(System.in);
            System.out.println("Enter your name: ");
            String name = in.nextLine();
            int id = 0;
            int currentId = 0;


            Random rand = new Random();
            int maxNumber = 130;
            int randomNumber = rand.nextInt(maxNumber) + 1;
            Date date = new Date();

            id = (int) (date.getTime() % (randomNumber * 2 + 3));

            System.out.println("Your id is: " + id);
            Player player = new Player(name, id, false);

            ReturnMessage msg = ticTacToe.enter(player);
            System.out.println(msg.getMessage());
            int opt = 0;
            int row = -1;
            int col = -1;
            boolean stopLoop = false;
            boolean validPlay;

            if(msg.getCode() == 1){
                do{
                        //ticTacToe.getNumOfPlayers();
                        //Checa se a quantidade de jogadores é suficiente
                        while(!ticTacToe.gameCanBePlayed()){
                            if(ticTacToe.getNumOfPlayers() == 1){
                                System.out.println("Waiting for other player to start the game...");
                                //Mudando o valor do stopLoop para true para parar o loop
                                TimeUnit.SECONDS.sleep(3);
                            }else {
                                System.out.println("Game started");
                            }
                        }
                        //Se a quantidade de jogadores for suficiente, o jogo pode ser iniciado
                       // System.out.println("Game started");
                        //stopLoop = false;

                        //Enquando o jogo não for finalizado, o loop será executado
                        while(!ticTacToe.checkWin()){
                            if(ticTacToe.checkTie()){
                                System.out.println("Game ended, it's a Tie!");
                                ticTacToe.exit();
                                return;
                            }
                            if(ticTacToe.getPlayerTurn(id)){
                                do {
                                    //Avisando de qual jogador é a sua vez e mostrando o tabuleiro
                                    System.out.println("Your turn," +player.getName() + "\nThe symbol will represent you in the game is: " + player.getId());
                                    System.out.println(ticTacToe.getBoard());

                                    //Pedindo a linha e a coluna
                                    System.out.println("Enter row: ");
                                    row = in.nextInt();
                                    System.out.println("Enter col: ");
                                    col = in.nextInt();

                                    //Armazenando se a jogada é válida
                                    validPlay = ticTacToe.isValidMove(row, col);

                                    //Se a jogada for valida, verifica se o jogo acabou ou não
                                    //Se não acabou troca de jogador
                                    if(validPlay){
                                        ticTacToe.play(player,row, col);
                                        //Se o jogador vencer nesta jogada,mostra o tabuleiro e termina o jogo
                                        if (ticTacToe.checkWin()) {
                                            System.out.println(ticTacToe.getBoard());
                                            System.out.println("You won! " + player.getName() + " ID:" + player.getId());
                                            ticTacToe.exit(); //Remove jogadores do jogo
                                            return;
                                        }
                                        ticTacToe.switchPlayer();
                                        stopLoop = false;
                                    }else  {
                                        //Se a jogada for inválida, avisa que a jogada é inválida
                                        System.out.println("Invalid play,try again");
                                    }
                                }while(!validPlay);
                            }
                            //Se não for a sua vez, avisa que é a vez do outro jogador
                            else{
                                if(!stopLoop){
                                    System.out.println("\n"+ ticTacToe.getBoard());
                                    System.out.println("Waiting  the other player to  make a play");
                                    stopLoop = true;
                                }
                            }
                        }
                        //Como a verificação de vencedor é feita durante a jogada do jogador
                        //Aqui mostra a mensagem de que o jogador perdeu!
                        System.out.println(ticTacToe.getBoard());
                        if(!ticTacToe.checkTie()) {
                            System.out.println("Game ended, you lost!");
                            ticTacToe.exit(); //Remove jogadores do jogo
                            return;
                        }
                }while (opt != 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}