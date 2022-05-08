package tictactoe;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

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
            int maxNumber = 3;
            int randomNumber = rand.nextInt(maxNumber) + 1;
            Date date = new Date();

            id = (int) (date.getTime() % (randomNumber * 6 + 3));

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
                    System.out.println("1 - Play");
                    opt = Integer.parseInt(in.nextLine());
                    if(opt == 1){
                        ticTacToe.getNumOfPlayers();

                        //Checa se a quantidade de jogadores é suficiente
                        while(!ticTacToe.gameCanBePlayed()){
                            if(!stopLoop) {
                                System.out.println("Waiting for other player to start the game");
                                //Mudando o valor do stopLoop para true para parar o loop
                                stopLoop = true;
                            }
                        }
                        //Se a quantidade de jogadores for suficiente, o jogo pode ser iniciado
                        System.out.println("Game started");
                        stopLoop = false;

                        //Enquando o jogo não for finalizado, o loop será executado
                        while(!ticTacToe.isGameOver()){
                            if(ticTacToe.getPlayerTurn(id)){
                                do {
                                    //Avisando de qual jogador é a sua vez e mostrando o tabuleiro
                                    System.out.println("Your turn," +player.getName() + "\nThe symbol will represent you in the game is: " + player.getId());
                                    System.out.println(ticTacToe.getBoard());

                                    //Pedindo a linha e a coluna
                                    System.out.println("Enter row: ");
                                    row = Integer.parseInt(in.nextLine());
                                    System.out.println("Enter col: ");
                                    col = Integer.parseInt(in.nextLine());

                                    //Armazenando se a jogada é válida
                                    validPlay = ticTacToe.isValidMove(row, col);

                                    //Se a jogada for validda, verifica se o jogo acabou
                                    //Se a jogada for inválida, avisa que a jogada é inválida
                                    if(validPlay) {
                                        ticTacToe.play(player,row, col);
                                        ticTacToe.switchPlayer();
                                        //Se o jogador vencer nesta jogada,mostra o tabuleiro e termina o jogo
                                        if (ticTacToe.isGameOver()) {
                                            System.out.println(ticTacToe.getBoard());
                                            System.out.println("You won! " + player.getName() + " ID:" + player.getId());
                                            ticTacToe.exit();
                                            return;
                                        }
                                        stopLoop = false;
                                    }else {
                                        System.out.println("Invalid play,try again");
                                    }
                                }while(!validPlay && ticTacToe.getNumOfPlayers() == 2);
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
                        //Se o jogo acabou e não for o vencedor
                        //Como a verificação de vencedor é feita no método play
                        //Aqui mostra a mensagem de que o jogador perdeu!
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