package service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import entity.Seat;
import entity.Show;
import entity.ShowMapper;
import entity.Ticket;

public class TicketService implements ITicketService {

  ShowMapper showMapper = new ShowMapper();
  SeatService seatService = new SeatService();
  ShowService showService = new ShowService();

  @Override
  public Map<String, Integer> reserveSeats(String seatNumber, Show show) {

    // seat, ticket number
    Map<String, Integer> reservations = new HashMap<>();
    List<String> seatList = Arrays.asList(seatNumber.toUpperCase().split("[\\\\s,]+"));
    try {
      for (String seatNum : seatList) {
        Seat seat = seatService.findSeat(seatNum, show);
        if (seat.isAvailable()) {
          reservations.put(seatNum, seat.generateTicketNumber());
        } else {
          System.err.printf("~ Seat {%s} not available.", seatNum);
        }
      }
    } catch (Exception e) {
      System.err.println("~ Seat not found. Please choose available seats appeared on the screen.");

    }
    return reservations;
  }

  public boolean seatsAvailable(String seatNumber, Show show) {
    List<String> seatList = Arrays.asList(seatNumber.toUpperCase().split(","));
    try {
      return seatList.stream().allMatch(val -> seatService.findSeat(val, show).isAvailable() == true);
    } catch (Exception e) {
      return false;
    }
  }

  public Ticket issueTicket(Show show, int ticketNumber, String seat, long phoneNumber) {
    try {
      Date today = new Date();
      Ticket ticket = new Ticket(ticketNumber, show.getShowNumber(), seat, phoneNumber, today);
      showMapper.addTicket(show, ticket);
      System.out.printf("[√] Ticket #%s reserved!", ticketNumber);
      return ticket;
    } catch (Exception e) {
      System.err.println("~ Error while issuing Ticket.");
    }
    return null;
  }

  public Ticket findTicketBySeatNumber(String seatNumber, Show show) {
    Set<Map.Entry<Integer, Ticket>> tickets = showMapper.getTicketMapper().entrySet();

    for (Map.Entry<Integer, Ticket> ticket : tickets) {
      if (ticket.getValue().getSeatNumber().equals(seatNumber)) {
        return ticket.getValue();
      }
    }
    return null;
  }

  public boolean cancelBooking(int ticketNum, long phoneNumber) {
    try {
      Ticket ticket = showMapper.getTicketMapper().get(ticketNum);

      if (ticket != null && ticket.getPhoneNumber() == phoneNumber) {

        Show show = showService.findShow(ticket.getShowNumber());
        Date today = new Date();
        long duration = today.getTime() - ticket.getBookDate().getTime();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        if (diffInMinutes < show.getCancellationPeriod()) {
          showMapper.getTicketMapper().remove(ticketNum);
          System.out.println("[√] Successfully cancelled your booking!");
          return true;
        } else {
          System.out.println("~ Cannot cancel booking now. Time duration to cancel booking has already expired.");
        }
      } else {
        System.out.println("~ Ticket # or Phone # incorrect.");
      }
    } catch (Exception e) {
      System.out.println("~ Error while cancelling booking");
    }
    return false;
  }

  public boolean isPhoneNumberTaken(long phone) {
    Set<Map.Entry<Integer, Ticket>> tickets = showMapper.getTicketMapper().entrySet();

    for (Map.Entry<Integer, Ticket> ticket : tickets) {
      Ticket t = ticket.getValue();
      if (t.getPhoneNumber() == phone) {
        System.out.println("[!] Phone number already in use. Use another one.");
        return true;
      }
    }
    return false;
  }

}
