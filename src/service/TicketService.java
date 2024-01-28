package service;

import entity.Mapper;
import entity.Seat;
import entity.Show;
import entity.Ticket;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TicketService implements ITicketService {

  Mapper mapper = new Mapper();
  SeatService seatService = new SeatService();
  ShowService showService = new ShowService();

  @Override
  public Map<Integer, Ticket> reserveSeats(final String seatNumber, final Show show) {
    Map<Integer, Ticket> reservations = new HashMap<>();
    List<String> seatList = Arrays.asList(seatNumber.toUpperCase().split("[\\\\s,]+"))
    ;
    try {
      for (String seatNum : seatList) {
        Seat seat = seatService.findSeat(seatNum, show);
        if (seat.isAvailable()) {
          Ticket ticket = new Ticket(
            seat.generateTicketNumber(),
            show.getShowNumber(),
            seatNum
          );
          reservations.put(ticket.getTicketNumber(), ticket);
        } else {
          System.err.printf("~ Seat {%s} not available.", seatNum);
        }
      }
    } catch (Exception e) {
      System.err.println(
        "~ Seat not found. Please choose available seats appeared on the screen."
      );
    }
    return reservations;
  }

  public boolean seatsAvailable(final String seatNumber, final Show show) {
    List<String> seatList = Arrays.asList(
      seatNumber.toUpperCase().split("[\\\\s,]+")
    );
    try {
      if (seatNumber.equals("0")) {
        return true;
      }
      return seatList
        .stream()
        .allMatch(val -> seatService.findSeat(val, show).isAvailable());
    } catch (Exception e) {
      return false;
    }
  }

  public Ticket issueTicket(final Ticket ticket, final Show show, final long phoneNumber) {
    try {
      Date today = new Date();
      ticket.setPhoneNumber(phoneNumber);
      ticket.setBookDate(today);

      if (mapper.addTicket(ticket)) {
        Seat currentSeat = seatService.findSeat(ticket.getSeatNumber(), show);
        currentSeat.setAvailability(false);
        System.out.printf("[√] Ticket #%s reserved!", ticket.getTicketNumber());
        return ticket;
      }
    } catch (Exception e) {
      System.err.println("Error while issuing Ticket.");
    }
    return null;
  }

  private Ticket findTicket(final int ticketNum) {
    return mapper.getTicketMapper().get(ticketNum);
  }

  public Ticket findTicketBySeatNumber(final String seatNumber, final Show show) {
    Map<Integer, Ticket> ticketMapper = mapper.getTicketMapper();
    Set<Map.Entry<Integer, Ticket>> tickets = ticketMapper.entrySet();

    for (Map.Entry<Integer, Ticket> ticket : tickets) {
      if (ticket.getValue().getSeatNumber().equals(seatNumber)) {
        return ticket.getValue();
      }
    }
    return null;
  }

  public boolean cancelBooking(final int ticketNum, final long phoneNumber) {
    try {
      Ticket ticket = findTicket(ticketNum);

      if (ticket != null && ticket.getPhoneNumber() == phoneNumber) {
        Show show = showService.findShow(ticket.getShowNumber());
        Date today = new Date();
        long duration = today.getTime() - ticket.getBookDate().getTime();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        if (diffInMinutes < show.getCancellationPeriod()) {
          Seat seat = seatService.findSeatByTicketNumber(ticket);
          seat.setAvailability(true);
          mapper.removeTicket(ticket);

          System.out.println("[√] Successfully cancelled your booking!");
          return true;
        } else {
          System.out.println("Cannot cancel booking now. Time duration to cancel booking has already expired.");
        }
      } else {
        System.out.println("Ticket # or Phone # incorrect.");
      }
    } catch (Exception e) {
      System.out.println("Error while cancelling booking");
    }
    return false;
  }

  public boolean isPhoneNumberTaken(long phone) {
    Map<Integer, Ticket> ticketMapper = mapper.getTicketMapper();
    Set<Map.Entry<Integer, Ticket>> tickets = ticketMapper.entrySet();

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
