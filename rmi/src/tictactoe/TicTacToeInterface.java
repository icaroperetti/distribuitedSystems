package tictactoe;

import java.rmi.Remote;

//Server
public interface TicTacToeInterface extends Remote {

    public ReturnMessage enter(Player player) throws Exception;


    public void play(Player player, int row, int col) throws Exception;

    public String getBoard() throws Exception;

    public Boolean gameCanBePlayed() throws Exception;

    public void switchPlayer() throws Exception;

    public Boolean checkTie() throws Exception;

    public Boolean checkWin() throws Exception;

    public Boolean isValidMove(int x, int y) throws Exception;

    public void exit() throws Exception;

    public Boolean getPlayerTurn(int id) throws Exception;

   public int getNumOfPlayers() throws Exception;
   public Boolean isGameOver() throws Exception;

}
