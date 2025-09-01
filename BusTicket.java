public class BusTicket {
    private String departureCity;
    private String arrivalCity;
    private String date;
    private String time;
    private int seatNumber;
    private boolean booked;

    public BusTicket(String departureCity, String arrivalCity, String date, String time, int seatNumber) {
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.date = date;
        this.time = time;
        this.seatNumber = seatNumber;
        this.booked = false;
    }

    public boolean isBooked() {
        return booked;
    }

    public void bookTicket() {
        booked = true;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    @Override
    public String toString() {
        return "BusTicket{" +
                "departureCity='" + departureCity + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", seatNumber=" + seatNumber +
                ", booked=" + booked +
                '}';
    }
}
