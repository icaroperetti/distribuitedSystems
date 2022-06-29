package ifc.sisdi.tpc.demo.exception;

public class ReplicaNotFoundException extends RuntimeException{
    public ReplicaNotFoundException(int id) {
        super("Replica with id " + id + " not found");
    }
}
