package entity;

import java.util.HashMap;
import java.util.Map;

import service.SeatService;

public class ShowMapper {

  // Show number, Show
  public static Map<Integer, Show> SHOW_INSTANCE = new HashMap<>();
  // Show number, Seat number, Seat
  public static Map<Integer, Map<String, Seat>> SHOW_SEAT_MAPPER = new HashMap<>();
  // Ticket number, ticket
  public static Map<Integer, Ticket> TICKET_MAPPER = new HashMap<>();

  public void addShow(int showNumber, Show show, Map<String, Seat> seats) {
    ShowMapper.SHOW_INSTANCE.put(showNumber, show);
    ShowMapper.SHOW_SEAT_MAPPER.put(showNumber, seats);
  }

  public void addTicket(Show show, Ticket ticket) {
    if (!ShowMapper.TICKET_MAPPER.containsKey(ticket.getTicketNumber())) {
      ShowMapper.TICKET_MAPPER.put(ticket.getTicketNumber(), ticket);

      SeatService seatService = new SeatService();

      Seat currentSeat = seatService.findSeat(ticket.getSeatNumber(), show);
      ShowMapper.SHOW_SEAT_MAPPER.get(show.getShowNumber())
          .replace(ticket.getSeatNumber(), new Seat(currentSeat.getSeatNumber(), false, show.getShowNumber()));
    } else {
      System.err.println("Ticket already exists.");
    }
  }

  public Map<Integer, Show> getShowInstance() {
    return ShowMapper.SHOW_INSTANCE;
  }

  public Map<Integer, Map<String, Seat>> getSeatMap() {
    return ShowMapper.SHOW_SEAT_MAPPER;
  }

  public Map<Integer, Ticket> getTicketMapper() {
    return ShowMapper.TICKET_MAPPER;
  }

  public Map<String, Seat> findSeatMapForShow(Show show) {
    if (ShowMapper.SHOW_SEAT_MAPPER.containsKey(show.getShowNumber())) {
      return ShowMapper.SHOW_SEAT_MAPPER.get(show.getShowNumber());
    }
    return null;
  }
}
