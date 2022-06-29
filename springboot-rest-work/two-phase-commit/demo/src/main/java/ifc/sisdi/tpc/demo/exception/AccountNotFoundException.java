package ifc.sisdi.tpc.demo.exception;

public class  AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(int id){
        super("Account not found with id: " + id);
    }
}
