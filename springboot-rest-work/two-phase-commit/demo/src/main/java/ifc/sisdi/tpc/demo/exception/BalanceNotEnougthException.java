package ifc.sisdi.tpc.demo.exception;

public class BalanceNotEnougthException extends RuntimeException{
    public BalanceNotEnougthException(int id) {
        super("Not enough money in account: " + id);
    }
}
