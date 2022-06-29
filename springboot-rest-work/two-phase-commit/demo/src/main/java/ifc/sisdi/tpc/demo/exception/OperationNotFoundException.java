package ifc.sisdi.tpc.demo.exception;

public class OperationNotFoundException extends RuntimeException {
    public OperationNotFoundException() {
        super("Invalid Operation only\n1 - Debit\n2 - Withdraw is allowed");
    }
}
