package tictactoe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

//Server
public class TicTacToe extends UnicastRemoteObject implements TicTacToeInterface {
    private List<Player> players;
    private int numOfPlayers;

    int board[][];


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

    public int getNumOfPlayers() {
        return this.numOfPlayers;
    }

    @Override
    public ReturnMessage enter(Player player) throws Exception {
        ReturnMessage returnMessage = new ReturnMessage();
        if(this.numOfPlayers < 2){
            if (!checkPlayer(player)){
                //Se for o primeiro jogador, será o primeiro a jogar
                if(players.isEmpty()){
                    player.setTurn(true); //Seta o jogador como o primeiro a jogar
                }
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
    //Retorna a matriz que representa o "tabuleiro"
    public String getBoard() throws RemoteException {
        StringBuilder boardString = new StringBuilder();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                boardString.append(board[i][j]).append("   ");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    @Override
    //Verifica se a jogada é válida
    public Boolean isValidMove(int x, int y) throws RemoteException {
        if(x <= 0 || x > 3 || y <= 0 || y > 3){
            return false;
        }
        if(board[x - 1 ][y - 1] == 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void exit(){
        this.players.clear();
        this.numOfPlayers = 0;
    }


    @Override
    //Verifica se o jogo possui 2 jogadores para que possa iniciar
    public Boolean gameCanBePlayed() throws RemoteException {
        return this.numOfPlayers == 2;
    }

    @Override
    //Faz a troca de jogador (turno)
    public void switchPlayer(){
        for(int i=0 ; i<players.size(); i++){
            if(players.get(i).getIsTurn() == true){
                players.get(i).setTurn(false);
            }else{
                players.get(i).setTurn(true);
            }
        }

    }
    @Override
    //Verifica se é a vez do jogador com este id jogar
    public Boolean getPlayerTurn(int id){
        for(Player p : players){
            if(p.getId() == id){
                return p.getIsTurn();
            }
        }
        return false;
    }

    @Override
    //Make the play
    public void play(Player player, int row, int col) throws Exception {
        this.board[row - 1][col - 1] = player.getId();
    }

    @Override
    //Check Tie (Velha)
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

    //Verifica se algum jogador ganhou
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

    public Boolean isGameOver(){
        return checkWin() || checkTie();
    }
}
