package service;

import entity.Seat;
import entity.Show;
import entity.ShowMapper;

public class SeatService implements ISeatService {

  ShowMapper showMapper = new ShowMapper();

  public Seat findSeat(String seatNumber, Show show) {
    return showMapper.getSeatMap().get(show.getShowNumber()).get(seatNumber);
  }

  public Seat findSeatByRowCol(int row, int col, Show show) {
    String seatEquivalent = getSeatValue(row, col);
    return findSeat(seatEquivalent, show);
  }

  public String getSeatValue(int row, int col) {
    String rowAlphaString = row > 0 && row < 27 ? String.valueOf((char) (row + 'A' - 1)) : null;
    return rowAlphaString + col;
  }
}
