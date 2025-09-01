
public class PaymentTree {
    private PaymentNode root;

    public PaymentTree() {
        root = null;
    }

    public void insert(String name,String bill_id, double amount) {
        if (root == null) {
            root = new PaymentNode(name,bill_id, amount);
        } else {
            root.insert(name,bill_id, amount);
        }
    }

    public void displayPayments() {
        if (root == null) {
            System.out.println("No payments made yet.");
        } else {
            System.out.println("Payments made:");
            root.traverseInOrder();
        }
    
}
}