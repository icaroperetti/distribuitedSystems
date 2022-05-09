package tictactoe;

import java.io.Serializable;

//Client
public class Player implements Serializable {
    private String name;
    private int id;

    private boolean isTurn;

    public Player(String name, int id, boolean isTurn) {
        this.name = name;
        this.id = id;
        this.isTurn = isTurn;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean getIsTurn() {
        return isTurn;
    }

    public void setTurn(boolean isTurn) {
        this.isTurn = isTurn;
    }
}