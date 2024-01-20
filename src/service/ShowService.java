package service;

import entity.Seat;
import entity.Show;
import entity.ShowMapper;
import entity.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShowService implements IShowService {

  public Show findShow(int showNumber) {
    return ShowMapper.SHOW_INSTANCE.get(showNumber);
  }

  public boolean createShow(int showNumber, int numOfRows, int numOfSeatsPerRow, int cancellationPeriod) {
    if (!ShowMapper.SHOW_INSTANCE.containsKey(showNumber)) {
      ShowMapper showMapper = new ShowMapper();
      Show show = new Show(showNumber, numOfRows, numOfSeatsPerRow, cancellationPeriod);
      try {
        showMapper.addShow(showNumber, show, generateSeats(show));
      } catch (Exception e) {
        System.err.println("Error while creating show. Try again.");
      }
      System.out.println("\nAdded a new show!");

      return true;
    } else {
      System.err.println("\nShow already exists. Try a different show number.");
    }
    return false;
  }

  public boolean displayShows() {
    Set<Map.Entry<Integer, Show>> showEntries = ShowMapper.SHOW_INSTANCE.entrySet();
    if (showEntries.isEmpty()) {
      System.out.println("-- Empty List. No shows added yet --\n\n");
      return false;
    }

    for (Map.Entry<Integer, Show> shows : showEntries) {
      Show show = shows.getValue();
      show.display();
    }
    return true;
  }

  public void displayShowDetails(Show show) {
    ShowMapper sm = new ShowMapper();
    Set<Map.Entry<String, Seat>> seats = sm.findSeatMapForShow(show).entrySet();
    if (seats.isEmpty()) {
      System.out.println("No data found.");
      return;
    }

    for (Map.Entry<String, Seat> seat : seats) {

      TicketService ticketService = new TicketService();
      Ticket ticket = ticketService.findTicketBySeatNumber(seat.getKey(), show);

      String ticketNum = "<Empty seat>";
      String phoneNum = "<Empty seat>";
      if (ticket != null) {
        ticketNum = String.valueOf(ticket.getTicketNumber());
        phoneNum = String.valueOf(ticket.getPhoneNumber());
      }
      System.out.printf("%-15s %s\n", "Seat #:", seat.getKey());
      System.out.printf("%-15s %s\n", "Ticket #:", ticketNum);
      System.out.printf("%-15s %s\n", "Phone #:", phoneNum);
      System.out.println();
    }
  }

  private Map<String, Seat> generateSeats(Show show) {
    Map<String, Seat> seats = new HashMap<>();
    int row = show.getNumOfRows();
    int col = show.getNumSeatsPerRow();
    for (int r = 1; r <= row; r++) {
      for (int c = 1; c <= col; c++) {

        SeatService seatService = new SeatService();
        Seat seat = new Seat(seatService.getSeatValue(r, c), true, show.getShowNumber());
        seats.put(seatService.getSeatValue(r, c), seat);

      }
    }
    return seats;
  }
}
