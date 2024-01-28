package service;

import entity.Show;
import entity.Ticket;
import java.util.Map;

public interface ITicketService {
  /**
   *
   * @param seatNumber
   * @param show
   * @return
   */
  Map<String, Integer> reserveSeats(String seatNumber, Show show);

  /**
   *
   * @param seatNumber
   * @param show
   * @return boolean
   */
  boolean seatsAvailable(String seatNumber, Show show);

  /**
   *
   * @param show
   * @param ticketNumber
   * @param seat
   * @param phoneNumber
   */
  Ticket issueTicket(Ticket ticket, Show show, long phoneNumber);

  /**
   *
   * @param seatNumber
   * @param show
   * @return Ticket
   */
  Ticket findTicketBySeatNumber(String seatNumber, Show show);

  /**
   *
   * @param ticketNum
   * @param phoneNumber
   * @return boolean
   */
  boolean cancelBooking(int ticketNum, long phoneNumber);

  /**
   *
   * @param phone
   * @return boolean
   */
  boolean isPhoneNumberTaken(long phone);
}
