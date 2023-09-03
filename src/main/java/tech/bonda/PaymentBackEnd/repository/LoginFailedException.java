package tech.bonda.PaymentBackEnd.repository;

public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}