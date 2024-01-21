package test.main.service;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import entity.Seat;
import entity.Show;
import service.SeatService;
import service.ShowService;
import service.TicketService;

public class SeatServiceTest {

  SeatService seatService = new SeatService();
  TicketService ticketService = new TicketService();
  ShowService showService = new ShowService();
  
  Show testShow;
  int ticketNum;
  int phoneNum;

  @Before
  public void init() {
    testShow  = new Show(101, 12, 4, 1);
    ticketNum = 123456;
    phoneNum = 123680;
  }

  @Test
  public void testFindSeat() {
    showService.createShow(101, 12, 4, 1);
    ticketService.issueTicket(testShow, ticketNum, "A1", 123680);
    assertTrue(seatService.findSeat("A1", testShow) instanceof Seat);
  }

  @Test
  public void testFindSeatByRowCol () {
    showService.createShow(101, 12, 4, 1);
    assertTrue(seatService.findSeatByRowCol(1, 1, testShow).getSeatNumber()
      .equals("A1"));
    assertTrue(seatService.findSeatByRowCol(1, 1, testShow)
      .getShowNumber() == testShow.getShowNumber());
  }

  @Test
  public void testGetSeatValue() {
    assertTrue(seatService.getSeatValue(26, 10).equals("Z10"));
    assertTrue(seatService.getSeatValue(20,4).equals("T4"));
    assertTrue(seatService.getSeatValue(1, 5).equals("A5"));
    assertTrue(seatService.getSeatValue(13, 9).equals("M9"));
    assertTrue(seatService.getSeatValue(3, 7).equals("C7"));
  }
}
