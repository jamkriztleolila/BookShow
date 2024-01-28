package entity;

import java.util.Date;

public class Seat {

  private String seatNumber;
  private boolean available = true;
  private Integer showNumber;

  public Seat(String seatNumber, int showNumber) {
    this.seatNumber = seatNumber;
    this.showNumber = showNumber;
  }

  public String getSeatNumber() {
    return seatNumber;
  }

  public boolean isAvailable() {
    return available;
  }

  public int getShowNumber() {
    return showNumber;
  }

  public void setAvailability(boolean available) {
    this.available = available;
  }

  public int generateTicketNumber() {
    int code = seatNumber.hashCode();
    code += 31 * showNumber.hashCode();
    code = code * (int) (new Date().getTime() / 1000);
    String scode = String.valueOf(Math.abs(code));
    return Integer.parseInt(scode.substring(0, 6));
  }
}
