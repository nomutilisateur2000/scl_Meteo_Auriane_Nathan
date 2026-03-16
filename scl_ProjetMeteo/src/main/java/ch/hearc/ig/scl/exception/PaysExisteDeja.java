package ch.hearc.ig.scl.exception;

public class PaysExisteDeja extends RuntimeException {
    public PaysExisteDeja(String message) {
        super(message);
    }
}
