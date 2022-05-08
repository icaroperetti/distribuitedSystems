package tictactoe;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", "localhost");
        try {
            Registry registry = LocateRegistry.createRegistry(12345);
            TicTacToeInterface game = new TicTacToe();

            registry.bind("TicTacToe", game);
            System.out.println("Server ready");

            while(true){
                if(game.isGameOver()){
                    if(game.getNumOfPlayers() == 0){
                        System.out.println("Game ended");
                        break;
                    }
                }
            }

        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
