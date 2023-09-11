package tech.bonda.PaymentBackEnd.config.ErrorHandling;

public class EGNexeption extends RuntimeException {
    public EGNexeption(String message) {
        super(message);
    }
}