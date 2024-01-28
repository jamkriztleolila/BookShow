package test.main.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import entity.Mapper;
import entity.Show;
import entity.Ticket;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import service.ShowService;
import service.TicketService;

public class TicketServiceTest {

  TicketService ticketService = new TicketService();
  ShowService showsService = new ShowService();
  Mapper mapper = new Mapper();

  Show testShow;
  Ticket testTicket;
  int ticketNum;
  int phoneNum;

  @Before
  public void init() {
    ticketNum = 123456;
    phoneNum = 123680;
    testShow = new Show(101, 12, 8, 1);
  }

  @Test
  public void testReserveSeats() {
    String expected = "A3";
    showsService.createShow(testShow);
    assertTrue(ticketService.reserveSeats("A3", testShow).containsKey(expected));
  }

  @Test
  public void testReserveSeats_notAvailable() {
    testTicket = new Ticket(ticketNum, testShow.getShowNumber(), "A1");

    showsService.createShow(testShow);
    ticketService.issueTicket(testTicket, testShow, 123680);
    assertTrue(ticketService.reserveSeats("A1", testShow).size() == 0);
    mapper.getTicketMapper().clear();
  }

  @Test
  public void testSeatsAvailable() {
    showsService.createShow(testShow);
    assertTrue(ticketService.seatsAvailable("C1,B2,C2", testShow));
  }

  @Test
  public void testSeatsAvailable_notFound() {
    showsService.createShow(testShow);
    assertFalse(ticketService.seatsAvailable("A1,B2,M2", testShow));
  }

  @Test
  public void testIssueTicket() {
    testTicket = new Ticket(ticketNum, testShow.getShowNumber(), "A1");
    ticketService.issueTicket(testTicket, testShow, phoneNum);
    assertTrue(mapper.getTicketMapper().containsKey(ticketNum));
    mapper.getTicketMapper().clear();
  }

  @Test
  public void testFindTicketBySeatNumber() {
    Date testDate = new Date();
    testTicket = new Ticket(ticketNum, testShow.getShowNumber(), "C5");
    testTicket.setBookDate(testDate);
    testTicket.setPhoneNumber(phoneNum);
    showsService.createShow(testShow);

    ticketService.issueTicket(testTicket, testShow, 123680);
    assertTrue(ticketService.findTicketBySeatNumber("C5", testShow).getTicketNumber() ==
      ticketNum);
    mapper.getTicketMapper().clear();
  }

  @Test
  public void testCancelBooking() {
    Date testDate = new Date();
    testTicket = new Ticket(ticketNum, testShow.getShowNumber(), "A2");
    testTicket.setBookDate(testDate);
    testTicket.setPhoneNumber(phoneNum);

    showsService.createShow(testShow);
    mapper.addTicket(testTicket);
    assertTrue(ticketService.cancelBooking(123456, 123680));
    mapper.getTicketMapper().clear();
  }

  @Test
  public void testCancelBooking_expired() {
    Date testDate = new Date(System.currentTimeMillis() - 3600 * 1000);
    testTicket = new Ticket(ticketNum, testShow.getShowNumber(), "A2");

    showsService.createShow(testShow);
    Ticket expected = new Ticket(ticketNum, testShow.getShowNumber(), "C5");

    expected.setBookDate(testDate);
    expected.setPhoneNumber(phoneNum);
    mapper.addTicket(testTicket);
    assertFalse(ticketService.cancelBooking(123456, 123680));
    mapper.getTicketMapper().clear();
  }

  @Test
  public void testIsPhoneNumberTaken() {
    testTicket = new Ticket(ticketNum, testShow.getShowNumber(), "A2");
    ticketService.issueTicket(testTicket, testShow, phoneNum);
    assertTrue(ticketService.isPhoneNumberTaken(phoneNum));
    mapper.getTicketMapper().clear();
  }

  @Test
  public void testIsPhoneNumber_NotTaken() {
    testTicket = new Ticket(ticketNum, testShow.getShowNumber(), "B5");
    ticketService.issueTicket(testTicket, testShow, 123681);
    assertFalse(ticketService.isPhoneNumberTaken(phoneNum));
    mapper.getTicketMapper().clear();
  }
}
