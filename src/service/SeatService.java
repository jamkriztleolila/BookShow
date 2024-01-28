package service;

import entity.Seat;
import entity.Show;
import entity.Ticket;
import entity.Mapper;

public class SeatService implements ISeatService {

  Mapper mapper = new Mapper();

  public Seat findSeat(String seatNumber, Show show) {
    Seat seat = mapper.getSeatMap().get(show.getShowNumber()).get(seatNumber);
    if (seat == null) {
      System.out.println("Seat not found.");
      return null;
    }
    return seat;
  }

  public Seat findSeatByRowCol(int row, int col, Show show) {
    String seatEquivalent = getSeatValue(row, col);
    return findSeat(seatEquivalent, show);
  }

  public Seat findSeatByTicketNumber(Ticket ticket) {
    ShowService showService = new ShowService();
    Show show = showService.findShow(ticket.getShowNumber());
    
    return findSeat(ticket.getSeatNumber(), show);
  }

  public String getSeatValue(int row, int col) {
    String rowAlphaString = row > 0 && row < 27 ? String.valueOf((char) (row + 'A' - 1)) : null;
    return rowAlphaString + col;
  }
}
