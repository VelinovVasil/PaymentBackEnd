package tech.bonda.PaymentBackEnd.config.ErrorHandling;

public class DuplicateEGNException extends RuntimeException {
    public DuplicateEGNException (String message) {
        super(message);
    }
}