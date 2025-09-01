public class First {
    private String phone;
    private String account;
    private int deposite;
    private int pre_amount;
    private int current_amount;
    private String type;

    public First(String account, String phone, int pre_amount, String type, int deposite, int current_amount) {
        this.account = account;
        this.phone = phone;
        this.deposite = deposite;
        this.pre_amount = pre_amount;
        this.current_amount = current_amount;
        this.type = type;
    }

    @Override
    public String toString() {
        return "\t\t------------------------------------------------------------\n\t\t Account : " + account + "\t\tPhone-Number : " + phone + "\n\t\t------------------------------------------------------------\n\t\t Previous-Amount : " + pre_amount + "  \t" + type + "-Amount : " + deposite + " \n\t\t------------------------------------------------------------\n\t\t\t\t Current-Amount : " + current_amount + "\n\t\t------------------------------------------------------------\n";
    }
}
