package service;

import entity.Seat;
import entity.Show;
import entity.Ticket;

public interface ISeatService {
  /**
   *
   * @param seatNumber
   * @param show
   * @return Seat
   */
  Seat findSeat(String seatNumber, Show show);

  /**
   *
   * @param row
   * @param col
   * @param show
   * @return Seat
   */
  Seat findSeatByRowCol(int row, int col, Show show);

  /**
   *
   * @param ticket
   * @return Seat
   */
  Seat findSeatByTicketNumber(Ticket ticket);

  /**
   *
   * @param row
   * @param col
   * @return String
   */
  String getSeatValue(int row, int col);
}
