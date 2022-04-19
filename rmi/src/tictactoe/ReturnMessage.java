package tictactoe;

/*
* 1 - OK
* 2 - Error - ID exists
* 3 - Full game
* FAZER UM ENUM
 */

import java.io.Serializable;

public class ReturnMessage implements Serializable {
    int code;
    String message;

    public ReturnMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ReturnMessage() {

    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
