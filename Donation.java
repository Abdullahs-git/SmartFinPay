public class Donation {
    private int donationAmount;

    public Donation(int donationAmount) {
        this.donationAmount = donationAmount;
    }

    public int getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(int donationAmount) {
        this.donationAmount = donationAmount;
    }

    @Override
    public String toString() {
        return "Donation Amount: " + donationAmount;
    }

}
