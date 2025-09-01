import java.util.Date;

public class Transaction {
    private Date date;
    private String accountNumber;
    private String type;
    private int amount;

    public Transaction(String accountNumber, String type, int amount) {
        this.date = new Date();
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Date: " + date + "\n" +
               "Account Number: " + accountNumber + "\n" +
               "Type: " + type + "\n" +
               "Amount: " + amount;
    }
}
