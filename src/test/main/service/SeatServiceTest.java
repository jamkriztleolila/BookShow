package test.main.service;

import static org.junit.Assert.assertTrue;

import entity.Seat;
import entity.Show;
import entity.Ticket;
import org.junit.Before;
import org.junit.Test;
import service.SeatService;
import service.ShowService;
import service.TicketService;

public class SeatServiceTest {

  SeatService seatService = new SeatService();
  TicketService ticketService = new TicketService();
  ShowService showService = new ShowService();

  Show testShow;
  Ticket testTicket;
  int ticketNum;
  int phoneNum;

  @Before
  public void init() {
    ticketNum = 123456;
    phoneNum = 123680;
    testShow = new Show(101, 12, 4, 1);
    testTicket = new Ticket(ticketNum, testShow.getShowNumber(), "A1");
  }

  @Test
  public void testFindSeat() {
    showService.createShow(testShow);
    ticketService.issueTicket(testTicket, testShow, phoneNum);
    assertTrue(seatService.findSeat("A1", testShow) instanceof Seat);
  }

  @Test
  public void testFindSeatByRowCol() {
    showService.createShow(testShow);
    assertTrue(
      seatService.findSeatByRowCol(3, 3, testShow).getSeatNumber().equals("C3")
    );
    assertTrue(
      seatService.findSeatByRowCol(3, 3, testShow).getShowNumber() ==
      testShow.getShowNumber()
    );
  }

  @Test
  public void testFindSeatByTicketNumber() {
    showService.createShow(testShow);
    ticketService.issueTicket(testTicket, testShow, phoneNum);
    assertTrue(
      seatService
        .findSeatByTicketNumber(testTicket)
        .getSeatNumber()
        .equals("A1")
    );
  }

  @Test
  public void testGetSeatValue() {
    assertTrue(seatService.getSeatValue(26, 10).equals("Z10"));
    assertTrue(seatService.getSeatValue(20, 4).equals("T4"));
    assertTrue(seatService.getSeatValue(1, 5).equals("A5"));
    assertTrue(seatService.getSeatValue(13, 9).equals("M9"));
    assertTrue(seatService.getSeatValue(3, 7).equals("C7"));
  }
}
