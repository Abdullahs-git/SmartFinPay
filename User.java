class User {
    private String username;
    private String password;
    private String accountNumber;
    private int balance;

    public User(String username, String password, String accountNumber, int balance) {
        this.username = username;
        this.password = password;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
