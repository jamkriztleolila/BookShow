package entity;

import java.util.Date;

public class Ticket {

  private int ticketNumber;
  private int showNumber;
  private String seatNumber;

  private long phoneNumber;
  private Date bookDate;

  public Ticket(int ticketNumber, int showNumber, String seatNumber) {
    this.showNumber = showNumber;
    this.seatNumber = seatNumber;
    this.ticketNumber = ticketNumber;
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

  public void setPhoneNumber(long phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setBookDate(Date bookDate) {
    this.bookDate = bookDate;
  }
}
