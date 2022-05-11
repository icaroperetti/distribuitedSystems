package tictactoe;

import tictactoe.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Server
public interface TicTacToeInterface extends Remote {

    public ReturnMessage enter(Player player) throws Exception;
    public void play(Player player, int row, int col) throws Exception;

    public String showBoard() throws Exception ;
    public int[][] getBoard() throws Exception;

    public Boolean isValidMove(int x, int y) throws Exception;

    public Boolean checkWin() throws Exception;

    public Boolean checkTie() throws Exception;

    public int getNumOfPlayers() throws Exception;

    public void removeAllPlayers() throws Exception;

    public boolean playerCanPlay(Player player) throws Exception;
}
