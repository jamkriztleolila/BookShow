package entity;

import java.util.HashMap;
import java.util.Map;

public class Mapper {

  // Show number, Show
  protected static Map<Integer, Show> showMapper = new HashMap<>();
  // Show number, Seat number, Seat
  protected static Map<Integer, Map<String, Seat>> seatMapper = new HashMap<>();
  // Ticket number, ticket
  protected static Map<Integer, Ticket> ticketMapper = new HashMap<>();

  public void addShow(int showNumber, Show show, Map<String, Seat> seats) {
    if (!showMapper.containsKey(show.getShowNumber())) {
        showMapper.put(showNumber, show);
        seatMapper.put(showNumber, seats);
    } else {
      System.err.println("\nShow already exists. Try a different show number.");
    }
  }

  public boolean addTicket(Ticket ticket) {
    if (!ticketMapper.containsKey(ticket.getTicketNumber())) {
      ticketMapper.put(ticket.getTicketNumber(), ticket);
      return true;
    } else {
      System.err.println("Ticket already exists.");
    }
    return false;
  }

  public Map<Integer, Show> getShowMapper() {
    return showMapper;
  }

  public Map<Integer, Map<String, Seat>> getSeatMap() {
    return seatMapper;
  }

  public Map<Integer, Ticket> getTicketMapper() {
    return ticketMapper;
  }

  public Map<String, Seat> findSeatMapForShow(Show show) {
    return seatMapper.get(show.getShowNumber());
  }

  public void removeTicket(Ticket ticket) {
    ticketMapper.remove(ticket.getTicketNumber());
  }
}
