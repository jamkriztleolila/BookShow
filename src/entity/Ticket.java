package entity;

import java.util.Date;

public class Ticket {

  private final int ticketNumber;
  private final int showNumber;
  private final String seatNumber;
  private final long phoneNumber;
  private final Date bookDate;

  public Ticket(int ticketNumber, int showNumber, String seatNumber, long phoneNumber, Date bookDate) {
    this.showNumber = showNumber;
    this.seatNumber = seatNumber;
    this.ticketNumber = ticketNumber;
    this.phoneNumber = phoneNumber;
    this.bookDate = bookDate;
  }

  public int getTicketNumber() {
    return ticketNumber;
  }

  public int getShowNumber() {
    return showNumber;
  }

  public String getSeatNumber() {
    return seatNumber;
  }

  public long getPhoneNumber() {
    return phoneNumber;
  }

  public Date getBookDate() {
    return bookDate;
  }
}
