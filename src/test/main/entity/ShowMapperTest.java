package test.main.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import entity.Seat;
import entity.Show;
import entity.Mapper;
import entity.Ticket;
import service.ShowService;

public class ShowMapperTest {

  ShowService showService = new ShowService();

  Mapper mapper;
  int testShowNumber;
  String testSeatNumber;
  int testTicketNumber;
  Show testShow;
  Seat testSeat;
  Ticket testTicket;
  

  @Before
  public void init() {
    mapper = new Mapper();
    testShowNumber = 101;
    testSeatNumber = "A1";
    testTicketNumber = 1001;
    
    testShow = new Show(testShowNumber, 12, 8, 1);
    testSeat = new Seat(testSeatNumber, true, testShowNumber);
    testTicket = new Ticket(testTicketNumber, testShowNumber, testSeatNumber, 343434, new Date());
  }
  
  @Test
  public void testAddShow() {
    Map<String, Seat> test = new HashMap<>();
    test.put("A1", testSeat);

    mapper.addShow(101, testShow, test);
    assertTrue(mapper.getShowMapper().containsKey(testShowNumber));
    assertTrue(mapper.getSeatMap().containsKey(testShowNumber));
  }

  @Test
  public void testAddTicket() {
    showService.createShow(testShowNumber, 12, 9, 3);
    mapper.addTicket(testShow, testTicket);
    assertTrue(mapper.getTicketMapper().containsKey(testTicketNumber));
  }

  @Test
  public void testFindSeatMapForShow() {
    showService.createShow(testShowNumber, 12, 9, 3);
    assertTrue(mapper.findSeatMapForShow(testShow).containsKey("A1"));
    assertFalse(mapper.findSeatMapForShow(testShow).containsKey("C10"));
    assertFalse(mapper.findSeatMapForShow(testShow).containsKey("K10"));
  }
}
