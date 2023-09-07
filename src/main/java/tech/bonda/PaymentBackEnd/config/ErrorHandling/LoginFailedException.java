package tech.bonda.PaymentBackEnd.config.ErrorHandling;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}