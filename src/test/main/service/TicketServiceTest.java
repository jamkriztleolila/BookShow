package test.main.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import entity.Show;
import entity.ShowMapper;
import entity.Ticket;
import service.ShowService;
import service.TicketService;

public class TicketServiceTest {

  TicketService ticketService = new TicketService();
  ShowService showsService = new ShowService();
  
  Show testShow;
  int ticketNum;
  int phoneNum;

  @Before
  public void init() {
    testShow = new Show(101, 12, 8, 1);
    ticketNum = 123456;
    phoneNum = 123680;
  }

  @Test
  public void testReserveSeats() {
    String expected = "A3";
    showsService.createShow(101, 12, 8, 1);
    assertTrue(ticketService.reserveSeats("A3", testShow).containsKey(expected));
  }

  @Test
  public void testReserveSeats_notAvailable() {
    showsService.createShow(testShow.getShowNumber(), testShow.getNumOfRows(), testShow.getNumSeatsPerRow(), testShow.getCancellationPeriod());
    ticketService.issueTicket(testShow, 12356, "A1", 123680);
    assertTrue(ticketService.reserveSeats("A1", testShow).size() == 0);
  }

  @Test 
  public void testSeatsAvailable() {
    showsService.createShow(testShow.getShowNumber(), testShow.getNumOfRows(), testShow.getNumSeatsPerRow(), testShow.getCancellationPeriod());
    assertTrue(ticketService.seatsAvailable("A1,B2,A2", testShow));
  }

  @Test 
  public void testSeatsAvailable_notFound() {
    showsService.createShow(testShow.getShowNumber(), testShow.getNumOfRows(), testShow.getNumSeatsPerRow(), testShow.getCancellationPeriod());
    assertFalse(ticketService.seatsAvailable("A1,B2,M2", testShow));
  }

  @Test
  public void testIssueTicket() {
    ticketService.issueTicket(testShow, ticketNum, "A1", phoneNum);

    ShowMapper sm = new ShowMapper();
    assertTrue(sm.getTicketMapper().containsKey(ticketNum));
  }

  @Test
  public void testFindTicketBySeatNumber() {
    showsService.createShow(testShow.getShowNumber(), testShow.getNumOfRows(), testShow.getNumSeatsPerRow(), testShow.getCancellationPeriod());
    
    Ticket expected = ticketService.issueTicket(testShow, ticketNum, "A2", 123680);
    assertTrue(ticketService.findTicketBySeatNumber("A2", testShow).equals(expected));
  }

  @Test
  public void testCancelBooking() {
    Date testDate = new Date();
    ShowMapper sm = new ShowMapper();

    showsService.createShow(testShow.getShowNumber(), testShow.getNumOfRows(), testShow.getNumSeatsPerRow(), testShow.getCancellationPeriod());
    Ticket expected = new Ticket(ticketNum, testShow.getShowNumber(), "A2", phoneNum, testDate);
    sm.addTicket(testShow, expected);

    assertTrue(ticketService.cancelBooking(123456, 123680));
  }

  @Test
  public void testCancelBooking_expired() {
    Date testDate = new Date(System.currentTimeMillis() - 3600 * 1000);
    ShowMapper sm = new ShowMapper();

    showsService.createShow(testShow.getShowNumber(), testShow.getNumOfRows(), testShow.getNumSeatsPerRow(), testShow.getCancellationPeriod());
    Ticket expected = new Ticket(ticketNum, testShow.getShowNumber(), "A2", phoneNum, testDate);
    sm.addTicket(testShow, expected);

    assertFalse(ticketService.cancelBooking(123456, 123680));
  }

  @Test
  public void testIsPhoneNumberTaken() {
    ticketService.issueTicket(testShow, ticketNum, "A2", phoneNum);
    
    assertTrue(ticketService.isPhoneNumberTaken(phoneNum));
  }

  @Test
  public void testIsPhoneNumber_NotTaken() {
    ticketService.issueTicket(testShow, ticketNum, "A2", 123681);
    
    assertFalse(ticketService.isPhoneNumberTaken(phoneNum));
  }

}
