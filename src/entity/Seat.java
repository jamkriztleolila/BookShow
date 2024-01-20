package entity;

public class Seat {
  private final String seatNumber;
  // private int row;
  // private int column;
  private final boolean available;
  private final Integer showNumber;

  public Seat(String seatNumber, boolean available, int showNumber) {
    this.seatNumber = seatNumber;
    this.available = available;
    this.showNumber = showNumber;
  }

  public String getSeatNumber() {
    return seatNumber;
  }

  public boolean isAvailable() {
    return available;
  }

  public int generateTicketNumber() {
    int code = seatNumber.hashCode();
    code += 31 * showNumber.hashCode();
    return code;
  }

}
