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
        ReturnMessage returnMessage = new ReturnMessage();
        if(this.numOfPlayers < 2){
            if (!checkPlayer(player)){
                this.players.add(player);
                returnMessage.setCode(1);
                returnMessage.setMessage("Player " + player.getName() + " has entered the game");
                numOfPlayers++;
                return returnMessage;
            }
            else{
                returnMessage.setCode(2);
                returnMessage.setMessage("Player " + player.getId() + " already exists");
                return returnMessage;
            }
        }else {
            returnMessage.setCode(3);
            returnMessage.setMessage("Game is full");
            return returnMessage;
        }
    }

    @Override
    public void play(Player player, int row, int col) throws Exception {
     //Make the plays

    }
}
