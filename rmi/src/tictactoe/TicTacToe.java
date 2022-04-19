package tictactoe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

//Server
public class TicTacToe extends UnicastRemoteObject implements TicTacToeInterface {
    private List<Player> players;
    private int numOfPlayers;


    protected TicTacToe() throws RemoteException {
        super();
        this.players = new ArrayList<>();
        this.numOfPlayers = 0;
    }

    @Override
    public String enter(Player player) throws Exception {
        if(numOfPlayers < 2){
            this.players.add(player);
            numOfPlayers++;
            return "Welcome to TicTacToe "+ player.getName() + "You are the player number" + numOfPlayers;
        }else {
            return "Sorry, the game is full";
        }
    }

    @Override
    public void play(Player player, int row, int col) throws Exception {
     //Make the plays

    }
}
