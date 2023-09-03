package tech.bonda.PaymentBackEnd.core.ErrorHandling;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}