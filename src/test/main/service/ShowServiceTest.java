package test.main.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import entity.Mapper;
import entity.Show;
import entity.Ticket;
import org.junit.Before;
import org.junit.Test;
import service.ShowService;
import service.TicketService;

public class ShowServiceTest {

  ShowService showService = new ShowService();
  TicketService ticketService = new TicketService();
  Mapper mapper = new Mapper();

  Show testShow;
  int ticketNum;
  int phoneNum;

  @Before
  public void init() {
    ticketNum = 123456;
    phoneNum = 123680;
    testShow = new Show(101, 12, 4, 1);
  }

  @Test
  public void testFindShow() {
    showService.createShow(testShow);
    assertTrue(showService.findShow(101) instanceof Show);
  }

  @Test
  public void testCreateShow() {
    showService.createShow(testShow);
    assertTrue(mapper.getShowMapper().containsKey(testShow.getShowNumber()));
  }

  @Test
  public void testCreateShow_alreadyExists() {
    Show testShow2 = new Show(101, 26, 4, 1);
    showService.createShow(testShow);
    showService.createShow(testShow2);
    assertFalse(mapper.getShowMapper().get(testShow2.getShowNumber()).getNumOfRows() ==
      testShow2.getNumOfRows());
  }

  @Test
  public void testDisplayShows() {
    Show testShow2 = new Show(102, 12, 4, 1);
    showService.createShow(testShow);
    showService.createShow(testShow2);
    assertTrue(showService.displayShows());
  }

  @Test
  public void testGenerateSeats() {
    Show testShow2 = new Show(101, 26, 10, 10);
    assertTrue(showService.generateSeats(testShow2).size() == 260);
  }
}
