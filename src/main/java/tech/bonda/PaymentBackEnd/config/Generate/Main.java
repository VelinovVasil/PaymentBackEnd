package tech.bonda.PaymentBackEnd.config.Generate;

public class Main {
    public static void main(String[] args) {
        AccountGenerator accountGenerator = new AccountGenerator();
        accountGenerator.run();

        CardGenerator cardGenerator = new CardGenerator();
        cardGenerator.run();

        TransactionGenerator transactionGenerator = new TransactionGenerator();
        transactionGenerator.run();

    }
}
