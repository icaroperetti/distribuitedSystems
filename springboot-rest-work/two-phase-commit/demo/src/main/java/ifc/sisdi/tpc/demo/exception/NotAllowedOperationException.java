package ifc.sisdi.tpc.demo.exception;

public class NotAllowedOperationException extends RuntimeException {

        public NotAllowedOperationException() {
            super("Operation failed");
        }

}
