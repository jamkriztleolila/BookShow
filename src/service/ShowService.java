package service;

import entity.Mapper;
import entity.Seat;
import entity.Show;
import entity.Ticket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShowService implements IShowService {

  Mapper mapper = new Mapper();

  public Show findShow(final int showNumber) {
    Show show = mapper.getShowMapper().get(showNumber);
    if (show == null) {
      System.out.println("Show not found.");
      return null;
    }
    return show;  
  }

  public void createShow(final Show show) {
    try {
      mapper.addShow(show.getShowNumber(), show, generateSeats(show));
      System.out.println("\nAdded a new show!");
    } catch (Exception e) {
      System.err.println("Error while creating show. Try again.");
    }
  }

  public boolean displayShows() {
    Map<Integer, Show> showMap = mapper.getShowMapper();
    Set<Map.Entry<Integer, Show>> showEntries = showMap.entrySet();

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

  public void displayShowDetails(final Show show) {
    Map<String, Seat> seatMap = mapper.findSeatMapForShow(show);
    Set<Map.Entry<String, Seat>> seats = seatMap.entrySet();

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
      System.out.printf("%-15s %s%n", "Seat #:", seat.getKey());
      System.out.printf("%-15s %s%n", "Ticket #:", ticketNum);
      System.out.printf("%-15s %s%n", "Phone #:", phoneNum);
      System.out.println();
    }
  }

  public Map<String, Seat> generateSeats(final Show show) {
    Map<String, Seat> seats = new HashMap<>();
    
    for (int r = 1; r <= show.getNumOfRows(); r++) {
      for (int c = 1; c <= show.getNumSeatsPerRow(); c++) {
        SeatService seatService = new SeatService();
        Seat seat = new Seat(
          seatService.getSeatValue(r, c),
          show.getShowNumber()
        );
        seats.put(seat.getSeatNumber(), seat);
      }
    }
    return seats;
  }
}
