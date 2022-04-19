package tictactoe;

import java.rmi.Remote;

//Server
public interface TicTacToeInterface extends Remote {

    public ReturnMessage enter(Player player) throws Exception;
    public void play(Player player, int row, int col) throws Exception;
}
