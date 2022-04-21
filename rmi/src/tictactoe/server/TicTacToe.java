package tictactoe.server;

import tictactoe.client.Player;

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

    private boolean checkPlayer(Player player) {
        for (Player p : players) {
            if (p.getId() == player.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ReturnMessage enter(Player player) throws Exception {
        ReturnMessage message = new ReturnMessage();
        if(this.numOfPlayers < 2){
            if (!checkPlayer(player)){
                this.players.add(player);
                message.setCode(1);
                message.setMessage("Player " + player.getName() + " has entered the game");
                numOfPlayers++;
                return message;
            }
            else{
                message.setCode(2);
                message.setMessage("Player " + player.getId() + " already exists");
                return message;
            }
        }else {
            message.setCode(3);
            message.setMessage("Game is full");
            return message;
        }
    }

    @Override
    public void play(Player player, int row, int col) throws Exception {
     //Make the plays

    }
}
