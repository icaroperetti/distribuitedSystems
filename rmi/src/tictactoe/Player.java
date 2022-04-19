package tictactoe;

import java.io.Serializable;

//Client
public class Player implements Serializable {
    private String name;
    private int id;

    public Player(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}
