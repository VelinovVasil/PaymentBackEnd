package tech.bonda.PaymentBackEnd.core.ErrorHandling;

public class DuplicateEGNException extends RuntimeException {
    public DuplicateEGNException (String message) {
        super(message);
    }
}