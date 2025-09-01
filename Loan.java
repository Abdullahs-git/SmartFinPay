
class Loan {
    private String accountNumber;
    private String CNICNumber;
    private int loanAmount;
    private boolean approved;

    public Loan(String accountNumber, String CNICNumber, int loanAmount) {
        this.accountNumber = accountNumber;
        this.CNICNumber = CNICNumber;
        this.loanAmount = loanAmount;
        this.approved = false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCNICNumber() {
        return CNICNumber;
    }


    public int getLoanAmount() {
        return loanAmount;
    }

    public boolean isApproved() {
        return approved;
    }

    public void approveLoan() {
        approved = true;
    }
}
