package tictactoe;

import tictactoe.Player;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

//Server
public class TicTacToe extends UnicastRemoteObject implements TicTacToeInterface {
    private List<Player> players;
    private int numOfPlayers;
    private int[][] board;
    private boolean hasPermission = true;
    private Player firstPlayer;
    private Player secondPlayer;


    protected TicTacToe() throws RemoteException {
        super();
        this.players = new ArrayList<>();
        this.numOfPlayers = 0;
        this.board = new int[3][3];
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
            returnMessage.setCode(1);
            returnMessage.setMessage("Player " + player.getName() + " has entered the game");
            if (this.numOfPlayers == 0) {
                this.firstPlayer = player;
            } else {
                this.secondPlayer = player;
            }
            numOfPlayers++;
            return returnMessage;
        }else {
            returnMessage.setCode(3);
            returnMessage.setMessage("Game is full");
            return returnMessage;
        }
    }

    public int getNumOfPlayers() {
        return this.numOfPlayers;
    }

    public void removeAllPlayers(){
        this.players.clear();
        this.numOfPlayers = 0;
    }

    public String showBoard() throws Exception {
        StringBuilder boardString = new StringBuilder();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3;j++){
                boardString.append(Integer.toString(board[i][j])).append(" ");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    public int[][] getBoard() throws Exception {
        return this.board;
    }

    @Override
    //Verifica se a jogada é válida
    public Boolean isValidMove(int x, int y) throws RemoteException {
        //Validação de coordenadas, não podendo ser superior a 3 e inferior ou igual a 0
        if(x <= 0 || x > 3 || y <= 0 || y > 3 ){
            return false;
        }
        //Validação de jogada, não podendo ser repetida
        if(board[x - 1 ][y - 1] == 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    //Verifica se existe vencedor
    public Boolean checkWin(){
        for(int i = 0; i < 3; i++) {
            //Verifica linhas
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] != 0) {
                    return true;
                }
            }

            //Verifica colunas
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] != 0) {
                    return true;
                }
            }
            //Verifica  diagonal principal
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
                if (board[0][0] != 0) {
                    return true;
                }
            }
            //Verifica  a diagonal secundária
            if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
                if (board[0][2] != 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    //Verifica se o jogador possui permissão para jogar
    public boolean playerCanPlay(Player player){
        if(firstPlayer != null && secondPlayer != null){
            if (player.getId() == firstPlayer.getId() && this.hasPermission){
                return true;
            }
            return player.getId() == secondPlayer.getId() && !this.hasPermission;
        }
        return false;
    }


    @Override
    //Verifica se deu velha
    public Boolean checkTie() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }

        }
        return true;
    }
    @Override
    public void play(Player player, int row, int col) throws Exception {
            //Atribui o valor ao board na linha e coluna -1
            this.board[row - 1][col - 1] = player.getId();
            //Realiza a troca de jogadores
            if (player.getId() == firstPlayer.getId()) {
                this.hasPermission = false;
            }
            if (player.getId() == secondPlayer.getId()) {
                this.hasPermission = true;
            }
    }
}
