public class PaymentNode {
    String name;
    double amount;
    String  bill_id;
    PaymentNode left;
    PaymentNode right;

    public PaymentNode(String name,String bill_id, double amount) {
        this.name = name;
        this.amount = amount;
		this.bill_id=bill_id;
        left = null;
        right = null;
}
	public void insert(String name,String bill_id, double amount) {
        if (amount < this.amount) {
            if (left == null) {
                left = new PaymentNode(name,bill_id, amount);
            } else {
                left.insert(name,bill_id, amount);
            }
        } else {
            if (right == null) {
                right = new PaymentNode(name,bill_id, amount);
            } else {
                right.insert(name,bill_id, amount);
            }
        }
}
    

    public void traverseInOrder() {
        if (left != null) {
            left.traverseInOrder();
        }
        System.out.println(name + " - " + amount+ " - "+bill_id);
        if (right != null) {
            right.traverseInOrder();
        }
    }    

}